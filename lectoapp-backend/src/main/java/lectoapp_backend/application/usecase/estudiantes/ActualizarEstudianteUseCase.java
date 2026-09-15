package lectoapp_backend.application.usecase.estudiantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.request.ActualizarEstudianteRequest;
import lectoapp_backend.application.mapper.EstudianteRequestMapper;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Actualiza la información básica de un estudiante.
 *
 * El estudiante únicamente podrá ser actualizado por el
 * docente al que pertenece.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;

    private final EstudianteRequestMapper requestMapper;

    private final CurrentUserService currentUserService;

    public Estudiante ejecutar(Long id, ActualizarEstudianteRequest request) {

        // Obtiene el docente autenticado
        Usuario docente = currentUserService.obtenerUsuarioActual();

        // Busca únicamente estudiantes pertenecientes al docente autenticado
        Estudiante estudiante = estudianteRepository
                .buscarPorIdYDocenteId(id, docente.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe un estudiante con id: " + id));

        // Actualiza únicamente los campos permitidos
        requestMapper.actualizarDesdeRequest(request, estudiante);

        return estudianteRepository.guardar(estudiante);

    }

}