package lectoapp_backend.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.response.UsuarioResponse;
import lectoapp_backend.application.mapper.UsuarioResponseMapper;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper responseMapper;

    public List<UsuarioResponse> ejecutar() {

        System.out.println("Entró al UseCase");

        var usuarios = usuarioRepository.listarTodos();

        System.out.println("Cantidad usuarios: " + usuarios.size());

        usuarios.forEach(u -> {
            System.out.println(
                    u.getId() + " - "
                    + u.getNombre() + " - "
                    + u.getCorreo() + " - "
                    + u.getRol()
            );
        });

        return usuarios.stream()
                .map(responseMapper::toResponse)
                .toList();
    }
}
