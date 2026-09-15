package lectoapp_backend.application.usecase.estadistica;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;

/**
 * Caso de uso encargado de obtener el resumen estadístico
 * de un estudiante.
 *
 * <p>
 * Este caso de uso coordina la consulta de información estadística
 * mediante el puerto de dominio {@link EstadisticaRepository} y valida
 * que el docente autenticado tenga acceso al estudiante.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ObtenerResumenEstadisticoEstudianteUseCase {

    private final EstadisticaRepository estadisticaRepository;
    
    private final EstudianteRepository estudianteRepository;
    
    private final CurrentUserService currentUserService;

    /**
     * Obtiene el resumen estadístico de un estudiante validando permisos.
     *
     * @param estudianteId identificador del estudiante.
     * @return resumen estadístico del estudiante.
     */
    @Transactional(readOnly = true)
    public ResumenEstadistica ejecutar(Long estudianteId) {

        Usuario usuarioActual = currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        ResumenEstadistica resumen =
                estadisticaRepository.obtenerResumenEstudiante(estudianteId);

        // Si el repositorio retornó un resumen vacío (sin actividades),
        // enriquecemos el nombre con los datos del estudiante ya cargado.
        if (resumen.getNombreCompleto() == null) {
            resumen.setNombreCompleto(
                    estudiante.getNombre() + " " + estudiante.getApellido());
        }

        return resumen;
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