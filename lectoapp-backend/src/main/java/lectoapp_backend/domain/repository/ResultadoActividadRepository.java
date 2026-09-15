package lectoapp_backend.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lectoapp_backend.domain.model.ResultadoActividad;

/**
 * Puerto del dominio para la persistencia
 * de los resultados de las actividades.
 */
public interface ResultadoActividadRepository {

    ResultadoActividad guardar(ResultadoActividad resultado);

    Optional<ResultadoActividad> buscarPorId(Long id);

    List<ResultadoActividad> listarPorEstudiante(Long estudianteId);

    Optional<ResultadoActividad> buscarUltimoResultado(
            Long estudianteId);

    Long contarPorEstudiante(Long estudianteId);

    Integer promedioPuntaje(Long estudianteId);

    BigDecimal promedioPorcentaje(Long estudianteId);

    /**
     * Obtiene los identificadores de las actividades
     * que el estudiante ha aprobado al menos una vez.
     */
    Set<Long> listarIdsActividadesAprobadas(
            Long estudianteId,
            BigDecimal porcentajeMinimo);
}