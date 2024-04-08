package dev.adrianalonso.dekra.quickprod.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}