package lectoapp_backend.application.usecase;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.response.UsuarioResponse;
import lectoapp_backend.application.mapper.UsuarioResponseMapper;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper ResponseMapper;

    public UsuarioResponse ejecutar(Long id){

        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseMapper.toResponse(usuario);

    }

}