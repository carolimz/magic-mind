package lectoapp_backend.application.usecase.resultadoactividad;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.ResumenProgresoResponse;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Obtiene el resumen general del progreso de un estudiante
 * para consultas de docentes y administradores.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerResumenEstudianteUseCase {

    private final ObtenerResumenProgresoUseCase
            obtenerResumenProgresoUseCase;

    private final EstudianteRepository estudianteRepository;

    private final CurrentUserService currentUserService;

    public ResumenProgresoResponse ejecutar(Long estudianteId) {

        Usuario usuarioActual =
                currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        return obtenerResumenProgresoUseCase
                .ejecutar(estudianteId);
    }

    private void validarPermiso(
            Usuario usuarioActual,
            Estudiante estudiante) {

        boolean esAdministrador =
                Rol.ADMIN.equals(usuarioActual.getRol());

        boolean esDocenteResponsable =
                Objects.equals(
                        estudiante.getDocenteId(),
                        usuarioActual.getId());

        if (!esAdministrador && !esDocenteResponsable) {

            throw new AccessDeniedException(
                    "El estudiante no pertenece al docente autenticado.");
        }
    }
}