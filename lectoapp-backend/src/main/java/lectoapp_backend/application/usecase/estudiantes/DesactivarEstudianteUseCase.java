package lectoapp_backend.application.usecase.estudiantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Realiza una eliminación lógica de un estudiante.
 *
 * Solamente el docente propietario del estudiante puede
 * realizar esta operación.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DesactivarEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;

    private final CurrentUserService currentUserService;

    public void ejecutar(Long id) {

        // Obtiene el docente autenticado
        Usuario docente = currentUserService.obtenerUsuarioActual();

        // Busca únicamente estudiantes pertenecientes al docente autenticado
        Estudiante estudiante = estudianteRepository
                .buscarPorIdYDocenteId(id, docente.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe un estudiante con id: " + id));

        // Eliminación lógica
        estudiante.setActivo(false);

        estudianteRepository.guardar(estudiante);

    }

}