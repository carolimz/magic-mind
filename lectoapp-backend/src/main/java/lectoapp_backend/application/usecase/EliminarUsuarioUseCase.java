package lectoapp_backend.application.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lectoapp_backend.domain.repository.UsuarioRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EliminarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public void ejecutar(Long id){

        if(usuarioRepository.buscarPorId(id).isEmpty()){

            throw new ResourceNotFoundException("Usuario no encontrado");

        }

        usuarioRepository.eliminar(id);

    }

}