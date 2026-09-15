package lectoapp_backend.application.service;


import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;

public interface JwtService {

    /**
     * Genera un JWT para el usuario autenticado.
     */
    String generarToken(Usuario usuario);

    /**
     * Genera un JWT para el estudiante autenticado.
     */
    String generarToken(Estudiante estudiante);

    /**
     * Extrae el correo contenido dentro del JWT.
     */
    String extraerCorreo(String token);

    /**
     * Extrae el ID de estudiante contenido dentro del JWT.
     */
    Long extraerId(String token);

    /**
     * Extrae el código de acceso contenido dentro del JWT.
     */
    String extraerCodigo(String token);

    /**
     * Extrae el rol contenido dentro del JWT.
     */
    String extraerRol(String token);

    /**
     * Verifica si el token es válido para un usuario.
     */
    boolean esTokenValido(String token, Usuario usuario);

    /**
     * Verifica si el token es válido para un estudiante.
     */
    boolean esTokenValidoEstudiante(String token, Estudiante estudiante);
}
