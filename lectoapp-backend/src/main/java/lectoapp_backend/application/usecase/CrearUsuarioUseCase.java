package lectoapp_backend.application.usecase;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.request.UsuarioRequest;
import lectoapp_backend.application.dto.response.UsuarioResponse;
import lectoapp_backend.application.mapper.UsuarioRequestMapper;
import lectoapp_backend.application.mapper.UsuarioResponseMapper;
import lectoapp_backend.application.service.PasswordEncoderService;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Registrar un nuevo usuario en el sistema.
 *
 * Antes de almacenar el usuario, la contraseña se encripta
 * utilizando PasswordEncoderService.
 */
@Service
@RequiredArgsConstructor
public class CrearUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioRequestMapper requestMapper;

    private final UsuarioResponseMapper responseMapper;

    private final PasswordEncoderService passwordEncoderService;

    public UsuarioResponse ejecutar(UsuarioRequest request){

        Usuario usuario = requestMapper.toDomain(request);

        // Encriptar la contraseña antes de guardarla
        usuario.actualizarPassword(
            passwordEncoderService.encriptar(usuario.getPassword())
        );

        Usuario guardado = usuarioRepository.guardar(usuario);

        return responseMapper.toResponse(guardado);

    }

}