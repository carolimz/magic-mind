package lectoapp_backend.application.usecase.estadistica;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.shared.enums.Rol;

/**
 * Caso de uso para obtener el resumen estadístico de todos los estudiantes
 * activos asignados a un docente.
 *
 * <p>
 * Si el usuario autenticado es un {@link Rol#DOCENTE}, solo puede consultar
 * sus propios estudiantes. Si es un {@link Rol#ADMIN}, puede pasar un
 * {@code docenteId} explícito para consultar los estudiantes de cualquier docente.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ObtenerResumenEstudiantesDocenteUseCase {

    private final EstadisticaRepository estadisticaRepository;

    private final CurrentUserService currentUserService;

    /**
     * Obtiene el resumen estadístico de los estudiantes del docente.
     *
     * <p>
     * Si {@code docenteId} es {@code null} o el usuario es {@link Rol#DOCENTE},
     * se usa el identificador del usuario autenticado. Un {@link Rol#ADMIN} puede
     * pasar un {@code docenteId} explícito para consultar a otro docente.
     * </p>
     *
     * @param docenteId identificador del docente a consultar (puede ser {@code null}).
     * @return lista de resúmenes estadísticos, ordenada por apellido y nombre.
     */
    @Transactional(readOnly = true)
    public List<ResumenEstadistica> ejecutar(Long docenteId) {

        Usuario usuarioActual = currentUserService.obtenerUsuarioActual();

        // Los docentes siempre consultan sus propios estudiantes
        // Los admins pueden consultar los de cualquier docente pasando docenteId
        Long idDocente = (docenteId != null && Rol.ADMIN.equals(usuarioActual.getRol()))
                ? docenteId
                : usuarioActual.getId();

        return estadisticaRepository.obtenerResumenEstudiantesPorDocente(idDocente);
    }
}
