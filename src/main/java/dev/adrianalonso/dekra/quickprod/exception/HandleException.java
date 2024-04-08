package dev.adrianalonso.dekra.quickprod.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

/**
 * Clase para manejar excepciones globales y específicas en la API.
 *
 * Los métodos gestionan excepciones específicas y las convierten
 * en un formato {@link ResponseEntity} estandarizado con un {@link HttpResponse} detallado.

 *
 * Métodos principales:
 * - {@code exception}: Para excepciones generales.
 * - {@code apiException}: Para excepciones personalizadas.
 * - {@code handleExceptionInternal}: Para excepciones no especificadas por otros handlers.
 * - {@code handleMethodArgumentNotValid}: Para errores de validación de argumentos.
 * - {@code badCredentialsException}: Para excepciones de credenciales incorrectas.
 * - {@code accessDeniedException}: Para excepciones de acceso denegado.
 * - {@code emptyResultDataAccessException}: Para excepciones de datos no encontrados.
 * - {@code disabledException}: Para excepciones de cuenta deshabilitada.
 * - {@code lockedException}: Para excepciones de cuenta bloqueada.
 * - {@code dataAccessException}: Para excepciones de acceso a datos.
 *
 *
 * La estructura de los handlers, marcados con {@code @ExceptionHandler}, solo capturarán
 * excepciones de las clases controladoras manteniendo asi el código lo más limpio posible.
 *
 */
@RestControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler implements ErrorController {

    /**
     * Maneja excepciones internas envolviéndolas en un {@link HttpResponse} estructurado.
     * Este método personaliza el cuerpo de la respuesta para todas las excepciones usando el formato estándar definido.
     *
     * @param exception La excepción que fue capturada.
     * @param body El cuerpo que se devolverá en la respuesta.
     * @param headers Los encabezados HTTP que se devolverán.
     * @param statusCode El código de estado HTTP que se devolverá.
     * @param request La solicitud web actual.
     * @return Un objeto {@link ResponseEntity} que contiene el {@link HttpResponse}.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .status(resolve(statusCode.value()))
                        .statusCode(statusCode.value())
                        .build(), statusCode);
    }

    /**
     * Gestiona errores de argumentos no válidos en métodos de controlador.
     * @param exception La excepción capturada.
     * @param headers Los encabezados HTTP de la respuesta.
     * @param statusCode El código de estado HTTP para la respuesta.
     * @param request La solicitud web en curso.
     * @return Una respuesta HTTP estructurada indicando el error específico.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(fieldMessage)
                        .developerMessage(exception.getMessage())
                        .status(resolve(statusCode.value()))
                        .statusCode(statusCode.value())
                        .build(), statusCode);
    }

    /**
     * Maneja la excepción de credenciales incorrectas.
     * @param exception La excepción de credenciales incorrectas.
     * @return Respuesta detallada del error con motivo y mensaje para desarrolladores.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(exception.getMessage() + ", Incorrect email or password")
                        .developerMessage(exception.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build(), BAD_REQUEST);
    }

    /**
     * Maneja excepciones generadas por la API.
     * @param exception La excepción API capturada.
     * @return Respuesta con detalles del error.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<HttpResponse> apiException(ApiException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build(), BAD_REQUEST);
    }

    /**
     * Maneja el acceso denegado.
     * @param exception La excepción de acceso denegado.
     * @return Respuesta con el error de acceso denegado.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason("Access denied. You don't have access")
                        .developerMessage(exception.getMessage())
                        .status(FORBIDDEN)
                        .statusCode(FORBIDDEN.value())
                        .build(), FORBIDDEN);
    }

    /**
     * Maneja excepciones generales no capturadas específicamente por otros manejadores.
     * @param exception La excepción general capturada.
     * @return Respuesta con los detalles del error encontrado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> exception(Exception exception) {
        System.out.println(exception);
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(exception.getMessage() != null ?
                                (exception.getMessage().contains("expected 1, actual 0") ? "Record not found" : exception.getMessage())
                                : "Some error occurred")
                        .developerMessage(exception.getMessage())
                        .status(INTERNAL_SERVER_ERROR)
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .build(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja las excepciones de resultados vacíos en consultas de base de datos.
     * @param exception La excepción de resultado vacío capturada.
     * @return Respuesta indicando que no se encontraron registros.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<HttpResponse> emptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(exception.getMessage().contains("expected 1, actual 0") ? "Record not found" : exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build(), BAD_REQUEST);
    }

    /**
     * Maneja excepciones cuando la cuenta de usuario está deshabilitada.
     * @param exception La excepción de cuenta deshabilitada.
     * @return Respuesta indicando que la cuenta del usuario está deshabilitada.
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> disabledException(DisabledException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .developerMessage(exception.getMessage())
                        .reason("User account is currently disabled")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value()).build()
                , BAD_REQUEST);
    }

    /**
     * Maneja excepciones cuando la cuenta de usuario está bloqueada.
     * @param exception La excepción de cuenta bloqueada.
     * @return Respuesta indicando que la cuenta del usuario está bloqueada.
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException(LockedException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .developerMessage(exception.getMessage())
                        .reason("User account is currently locked")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value()).build()
                , BAD_REQUEST);
    }

    /**
     * Maneja excepciones de acceso a datos que pueden incluir errores de duplicidad de entrada, entre otros.
     * @param exception La excepción de acceso a datos.
     * @return Respuesta detallando el error de acceso a datos.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<HttpResponse> dataAccessException(DataAccessException exception) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(processErrorMessage(exception.getMessage()))
                        .developerMessage(processErrorMessage(exception.getMessage()))
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value()).build()
                , BAD_REQUEST);
    }

    /**
     * Procesa mensajes de error específicos relacionados con accesos duplicados y otras condiciones, ofreciendo mensajes claros y directos.
     * @param errorMessage El mensaje de error recibido.
     * @return Un mensaje de error procesado más amigable para el usuario.
     */
    private String processErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("AccountVerifications")) {
                return "You already verified your account.";
            }
            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("ResetPasswordVerifications")) {
                return "We already sent you an email to reset your password.";
            }
            if (errorMessage.contains("Duplicate entry")) {
                return "Duplicate entry. Please try again.";
            }
        }
        return "Some error occurred";
    }
}