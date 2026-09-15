package lectoapp_backend.application.usecase.estadistica;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;

/**
 * Caso de uso para obtener los errores más frecuentes de un estudiante.
 *
 * <p>
 * Los errores se extraen del campo JSONB {@code errores} de la tabla
 * {@code resultado_actividad}. Se agrupan por tipo y se ordenan
 * de mayor a menor frecuencia.
 * </p>
 *
 * <p>
 * Valida que el docente autenticado sea el responsable del estudiante
 * antes de exponer la información.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ObtenerErroresFrecuentesEstudianteUseCase {

    private final EstadisticaRepository estadisticaRepository;

    private final EstudianteRepository estudianteRepository;

    private final CurrentUserService currentUserService;

    /**
     * Obtiene los tipos de error más frecuentes de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @param limite       número máximo de tipos de error a retornar.
     * @return lista de errores frecuentes ordenada por frecuencia descendente.
     */
    @Transactional(readOnly = true)
    public List<ErrorFrecuente> ejecutar(Long estudianteId, int limite) {

        Usuario usuarioActual = currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        return estadisticaRepository.obtenerErroresFrecuentesEstudiante(estudianteId, limite);
    }

    private void validarPermiso(Usuario usuarioActual, Estudiante estudiante) {

        boolean esAdministrador = Rol.ADMIN.equals(usuarioActual.getRol());

        boolean esDocenteResponsable = Objects.equals(
                estudiante.getDocenteId(),
                usuarioActual.getId());

        if (!esAdministrador && !esDocenteResponsable) {
            throw new AccessDeniedException("El estudiante no pertenece al docente autenticado.");
        }
    }
}
