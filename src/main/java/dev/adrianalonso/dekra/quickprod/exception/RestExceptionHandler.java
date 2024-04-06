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

    @AllArgsConstructor
    @Setter
    @Getter
    public static class RESTFailure {
        private HttpStatus status;
        private String message;
    }
}
