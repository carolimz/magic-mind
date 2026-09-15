package lectoapp_backend.application.usecase.estudiantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;
    private final CurrentUserService currentUserService;

    public Estudiante ejecutar(Long id) {

        Usuario docente = currentUserService.obtenerUsuarioActual();

        return estudianteRepository
                .buscarPorIdYDocenteId(id, docente.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estudiante no encontrado."));

    }

}