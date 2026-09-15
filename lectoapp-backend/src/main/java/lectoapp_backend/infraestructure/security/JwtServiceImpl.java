package lectoapp_backend.infraestructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lectoapp_backend.application.service.JwtService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;

/**
 * Responsabilidad:
 * Implementar la generación y validación de tokens JWT.
 *
 * Esta clase encapsula toda la lógica relacionada con JWT,
 * evitando que el resto de la aplicación dependa directamente
 * de la librería JJWT.
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un JWT para el usuario autenticado.
     */
    @Override
    public String generarToken(Usuario usuario) {

        Date fechaActual = new Date();

        Date fechaExpiracion = new Date(
                fechaActual.getTime() + expiration
        );

        return Jwts.builder()
                .subject(usuario.getCorreo())
                .claim("rol", usuario.getRol().name())
                .issuedAt(fechaActual)
                .expiration(fechaExpiracion)
                .signWith(obtenerClave())
                .compact();
    }

    /**
     * Genera un JWT para el estudiante autenticado.
     */
    @Override
    public String generarToken(Estudiante estudiante) {

        Date fechaActual = new Date();

        Date fechaExpiracion = new Date(
                fechaActual.getTime() + expiration
        );

        return Jwts.builder()
                .subject(String.valueOf(estudiante.getId()))
                .claim("id", estudiante.getId())
                .claim("codigo", estudiante.getCodigoAcceso())
                .claim("rol", "ESTUDIANTE")
                .issuedAt(fechaActual)
                .expiration(fechaExpiracion)
                .signWith(obtenerClave())
                .compact();
    }

    /**
     * Extrae el correo almacenado dentro del JWT.
     */
    @Override
    public String extraerCorreo(String token) {

        return obtenerClaims(token).getSubject();

    }

    /**
     * Extrae el ID de estudiante contenido en el JWT.
     */
    @Override
    public Long extraerId(String token) {

        Object idClaim = obtenerClaims(token).get("id");

        if (idClaim instanceof Number) {
            return ((Number) idClaim).longValue();
        }

        return idClaim == null ? null : Long.valueOf(idClaim.toString());

    }

    /**
     * Extrae el código de acceso contenido en el JWT.
     */
    @Override
    public String extraerCodigo(String token) {

        return obtenerClaims(token).get("codigo", String.class);

    }

    /**
     * Extrae el rol contenido dentro del JWT.
     */
    @Override
    public String extraerRol(String token) {

        return obtenerClaims(token).get("rol", String.class);

    }

    /**
     * Valida que el token pertenezca al usuario y que no esté expirado.
     */
    @Override
    public boolean esTokenValido(String token, Usuario usuario) {

        String correo = extraerCorreo(token);

        return correo.equals(usuario.getCorreo())
                && !estaExpirado(token);

    }

    /**
     * Valida que el token pertenezca al estudiante y que no esté expirado.
     */
    @Override
    public boolean esTokenValidoEstudiante(String token, Estudiante estudiante) {

        Long id = extraerId(token);
        String codigo = extraerCodigo(token);

        return id != null
                && id.equals(estudiante.getId())
                && codigo.equals(estudiante.getCodigoAcceso())
                && !estaExpirado(token);

    }

    /**
     * Verifica si el token ya expiró.
     */
    private boolean estaExpirado(String token) {

        return obtenerClaims(token)
                .getExpiration()
                .before(new Date());

    }

    /**
     * Obtiene todos los Claims contenidos en el JWT.
     */
    private Claims obtenerClaims(String token) {

        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    /**
     * Construye la clave utilizada para firmar y validar
     * los tokens JWT.
     */
    private SecretKey obtenerClave() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }

}