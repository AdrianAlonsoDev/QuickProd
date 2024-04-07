package dev.adrianalonso.dekra.quickprod.keycloack.user;

import dev.adrianalonso.dekra.quickprod.exception.EmailVerificationException;
import dev.adrianalonso.dekra.quickprod.exception.UserNotFoundException;
import dev.adrianalonso.dekra.quickprod.exception.UserRegistrationException;
import dev.adrianalonso.dekra.quickprod.keycloack.auth.UserRegistrationRecord;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord) {
        UserRepresentation user = buildUserRepresentation(userRegistrationRecord);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            throw new UserRegistrationException("Error al crear usuario, código de respuesta: " + response.getStatus());
        }
        verifyEmail(user.getUsername(), usersResource);
        return userRegistrationRecord;

    }

    private UserRepresentation buildUserRepresentation(UserRegistrationRecord record) {
        //Comprobamos si los campos username, email, firstName y lastName están vacíos
        if (!StringUtils.hasText(record.username()) || !StringUtils.hasText(record.email()) ||
                !StringUtils.hasText(record.firstName()) || !StringUtils.hasText(record.lastName())) {
            throw new UserRegistrationException("Datos de registro de usuario inválidos");
        }

        //Comprobamos si username proveniente del record ya existe
        if(usernameExists(record.username())) {
            throw new UserRegistrationException("Username en uso");
        }

        //Comprobamos si el campo de la contraseña está vacío
        if (!StringUtils.hasText(record.password())) {
            throw new UserRegistrationException("Contraseña inválida");
        }

        //Creamos un objeto UserRepresentation y le asignamos los valores del record
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(record.username());
        user.setEmail(record.email());
        user.setFirstName(record.firstName());
        user.setLastName(record.lastName());
        user.setEmailVerified(false);

        //Añadimos la contraseña al usuario
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setValue(record.password());
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        user.setCredentials(Collections.singletonList(credential));

        return user;
    }

    private void verifyEmail(String username, UsersResource usersResource) {
        List<UserRepresentation> users = usersResource.searchByUsername(username, true);
        //Comprobamos si el usuario ya está verificado
        UserRepresentation userToVerify = users.stream()
                .filter(u -> !u.isEmailVerified())
                .findFirst()
                .orElseThrow(() -> new EmailVerificationException("Usuario ya verificado"));
        emailVerification(userToVerify.getId());
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public UserRepresentation getUserByUsername(String username) {
        if(!usernameExists(username)){
            throw new UserNotFoundException("Usuario no encontrado con Username: " + username);
        }
        return getUsersResource().searchByUsername(username, true).get(0);
    }

    @Override
    public void deleteUserById(String userId) {
        try {
            getUsersResource().delete(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("Usuario no encontrado con ID: " + userId + " " + e.getMessage());
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        if(!usernameExists(username)){
            throw new UserNotFoundException("Usuario no encontrado con Username: " + username);
        }
        getUsersResource().searchByUsername(username, true)
                .forEach(user -> getUsersResource().delete(user.getId()));
    }

    @Override
    public boolean usernameExists(String username) {
        List<UserRepresentation> users = getUsersResource().searchByUsername(username, true);
        //Retorna true si hay usuarios, false si no hay ninguno
        return !users.isEmpty();
    }

    @Override
    public void emailVerification(String userId){
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }
}