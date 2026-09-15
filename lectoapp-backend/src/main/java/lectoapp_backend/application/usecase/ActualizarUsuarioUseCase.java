package lectoapp_backend.application.usecase;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.request.UsuarioRequest;
import lectoapp_backend.application.dto.response.UsuarioResponse;
import lectoapp_backend.application.mapper.UsuarioResponseMapper;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualizarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper responseMapper;

    public UsuarioResponse ejecutar(Long id, UsuarioRequest request){

        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        usuario.actualizar(
                request.getNombre(),
                request.getApellido(),
                request.getCorreo(),
                request.getRol()
        );

        Usuario actualizado = usuarioRepository.guardar(usuario);

        return responseMapper.toResponse(actualizado);

    }

}