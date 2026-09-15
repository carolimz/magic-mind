package lectoapp_backend.application.usecase.estadistica;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;

/**
 * Caso de uso para obtener el progreso por etapas de un estudiante.
 *
 * <p>
 * Valida que el docente autenticado sea el responsable del estudiante
 * antes de exponer la información. Solo el docente asociado o un
 * administrador pueden consultar esta información.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ObtenerProgresoEtapasEstudianteUseCase {

    private final EstadisticaRepository estadisticaRepository;

    private final EstudianteRepository estudianteRepository;

    private final CurrentUserService currentUserService;

    /**
     * Obtiene el progreso por etapas de un estudiante validando permisos.
     *
     * @param estudianteId identificador del estudiante.
     * @return lista con el progreso por cada etapa pedagógica.
     */
    @Transactional(readOnly = true)
    public List<ProgresoEtapa> ejecutar(Long estudianteId) {

        Usuario usuarioActual = currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        return estadisticaRepository.obtenerProgresoPorEtapas(estudianteId);
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
