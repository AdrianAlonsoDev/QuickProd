package dev.adrianalonso.dekra.quickprod.exception;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException(String message) {
        super(message);
    }
}
