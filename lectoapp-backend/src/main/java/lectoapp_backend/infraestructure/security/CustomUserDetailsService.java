package lectoapp_backend.infraestructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Adaptar el repositorio de usuarios al mecanismo de autenticación
 * de Spring Security.
 *
 * Spring Security utiliza esta clase para cargar un usuario
 * a partir de su correo electrónico durante el proceso de login.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .buscarPorCorreo(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado."
                        ));

        return new SecurityUser(usuario);
    }

}