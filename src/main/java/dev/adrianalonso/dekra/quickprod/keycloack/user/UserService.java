package dev.adrianalonso.dekra.quickprod.keycloack.user;

import dev.adrianalonso.dekra.quickprod.keycloack.auth.UserRegistrationRecord;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {

    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    UserRepresentation getUserById(String userId);
    UserRepresentation getUserByUsername(String userId);
    void deleteUserById(String userId);
    void deleteUserByUsername(String username);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
    boolean usernameExists(String username);
}
