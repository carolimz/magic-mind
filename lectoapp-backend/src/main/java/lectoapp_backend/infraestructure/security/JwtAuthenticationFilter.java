package lectoapp_backend.infraestructure.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lectoapp_backend.application.service.JwtService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Interceptar cada petición HTTP para validar el JWT enviado
 * por el cliente.
 *
 * Si el token es válido, registra al usuario autenticado
 * dentro del contexto de seguridad de Spring.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    private final UsuarioRepository usuarioRepository;

    private final EstudianteRepository estudianteRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no existe Authorization continúa la petición.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae únicamente el JWT.
        String token = authHeader.substring(7);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String rol = jwtService.extraerRol(token);

                UserDetails userDetails = null;

                if ("ESTUDIANTE".equals(rol)) {
                    Long estudianteId = jwtService.extraerId(token);

                    Estudiante estudiante = estudianteRepository
                            .buscarPorId(estudianteId)
                            .orElseThrow(() ->
                                    new RuntimeException("Estudiante no encontrado."));

                    if (jwtService.esTokenValidoEstudiante(token, estudiante)) {
                        userDetails = new SecurityStudent(estudiante);
                    }
                } else {
                    String correo = jwtService.extraerCorreo(token);

                    userDetails = userDetailsService.loadUserByUsername(correo);

                    Usuario usuario = usuarioRepository
                            .buscarPorCorreo(correo)
                            .orElseThrow(() ->
                                    new RuntimeException("Usuario no encontrado."));

                    if (!jwtService.esTokenValido(token, usuario)) {
                        userDetails = null;
                    }
                }

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            } catch (JwtException | IllegalArgumentException ex) {
                // Token inválido o mal formado. No autenticamos.
            }
        }

        filterChain.doFilter(request, response);

    }

}