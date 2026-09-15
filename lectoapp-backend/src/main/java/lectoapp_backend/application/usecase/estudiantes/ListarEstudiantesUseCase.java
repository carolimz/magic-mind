package lectoapp_backend.application.usecase.estudiantes;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarEstudiantesUseCase {

    private final EstudianteRepository estudianteRepository;
    private final CurrentUserService currentUserService;

    public List<Estudiante> ejecutar() {

        Usuario docente = currentUserService.obtenerUsuarioActual();

        return estudianteRepository.listarPorDocenteId(docente.getId());

    }

}