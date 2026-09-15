package lectoapp_backend.shared.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Errores de validación en los DTO.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Error de validación.");

        ApiError error = construirError(
                HttpStatus.BAD_REQUEST,
                mensaje,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Credenciales incorrectas durante el login.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> manejarCredencialesIncorrectas(
            BadCredentialsException ex,
            HttpServletRequest request) {

        ApiError error = construirError(
                HttpStatus.UNAUTHORIZED,
                "Correo o contraseña incorrectos.",
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    /**
     * Usuario autenticado que no tiene el rol requerido.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> manejarAccesoDenegado(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ApiError error = construirError(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para realizar esta operación.",
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    /**
     * Recurso inexistente.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> manejarRecursoNoEncontrado(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = construirError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    /**
     * Error interno no contemplado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGeneral(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        ApiError error = construirError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error interno.",
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    /**
     * Error de comunicación con servicios externos (ej. API de Gemini).
     */
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiError> manejarErrorServicioExterno(
            RestClientException ex,
            HttpServletRequest request) {

        ApiError error = construirError(
                HttpStatus.SERVICE_UNAVAILABLE,
                "El servicio de Inteligencia Artificial no está disponible en este momento. Intente más tarde.",
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    /**
     * Construye el formato uniforme de error.
     */
    private ApiError construirError(
            HttpStatus status,
            String mensaje,
            String path) {

        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(mensaje)
                .path(path)
                .build();
    }
}