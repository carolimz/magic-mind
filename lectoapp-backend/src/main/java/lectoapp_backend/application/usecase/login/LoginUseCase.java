package lectoapp_backend.application.usecase.login;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.request.LoginRequest;
import lectoapp_backend.application.dto.response.LoginResponse;
import lectoapp_backend.application.service.JwtService;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Autenticar un usuario mediante correo y contraseña.
 *
 * Si las credenciales son válidas genera un JWT y devuelve
 * la información necesaria para que el frontend mantenga
 * la sesión del usuario.
 */
@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UsuarioRepository usuarioRepository;

    /**
     * Ejecuta el proceso de autenticación.
     */
    public LoginResponse ejecutar(LoginRequest request) {

        // Spring Security valida automáticamente
        // el correo y la contraseña.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository
                .buscarPorCorreo(request.getCorreo())
                .orElseThrow();

        String token = jwtService.generarToken(usuario);

        return LoginResponse.builder()
                .token(token)
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name())
                .build();

    }

}