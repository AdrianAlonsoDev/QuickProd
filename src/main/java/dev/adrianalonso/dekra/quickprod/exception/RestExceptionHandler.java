package dev.adrianalonso.dekra.quickprod.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RESTFailure handleResourceNotFoundException() {
        return new RESTFailure(HttpStatus.NOT_FOUND, "Resource not found");
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RESTFailure handleUserRegistrationException(UserRegistrationException ex) {
        return new RESTFailure(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RESTFailure handleUserNotFoundException(UserNotFoundException ex) {
        return new RESTFailure(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmailVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RESTFailure handleEmailVerificationException(EmailVerificationException ex) {
        return new RESTFailure(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PasswordUpdateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RESTFailure handlePasswordUpdateException(PasswordUpdateException ex) {
        return new RESTFailure(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(QuickprodInternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RESTFailure handleQuickprodInternalException(QuickprodInternalException ex) {
        return new RESTFailure(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class RESTFailure {
        private HttpStatus status;
        private String message;
    }
}
