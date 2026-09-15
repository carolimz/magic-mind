package lectoapp_backend.infraestructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Obtener el usuario autenticado desde el contexto de seguridad
 * de Spring Security.
 *
 * Esta clase adapta Spring Security a la arquitectura de la
 * aplicación, evitando que los casos de uso dependan
 * directamente del framework.
 */
@Service
@RequiredArgsConstructor
public class SecurityCurrentUserService implements CurrentUserService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene el usuario actualmente autenticado.
     */
    @Override
    public Usuario obtenerUsuarioActual() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No existe un usuario autenticado.");
        }

        String correo = authentication.getName();

        return usuarioRepository
                .buscarPorCorreo(correo)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado."));
    }

}