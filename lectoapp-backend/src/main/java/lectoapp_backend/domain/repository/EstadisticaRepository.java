package lectoapp_backend.domain.repository;

import java.util.List;

import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.ResumenEstadistica;

/**
 * Puerto de salida para las consultas estadísticas de LectoApp.
 *
 * <p>
 * Define las operaciones que necesita la capa de aplicación para consultar
 * información estadística sin depender de la tecnología utilizada para
 * almacenar o consultar los datos.
 * </p>
 *
 * <p>
 * La implementación concreta pertenece a la capa de infraestructura.
 * </p>
 */
public interface EstadisticaRepository {

    /**
     * Obtiene el resumen estadístico de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @return resumen estadístico del estudiante.
     */
    ResumenEstadistica obtenerResumenEstudiante(Long estudianteId);

    /**
     * Obtiene el progreso desglosado por etapa de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @return lista de progreso por cada etapa.
     */
    List<ProgresoEtapa> obtenerProgresoPorEtapas(Long estudianteId);

    /**
     * Obtiene los errores más frecuentes de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @param limite número máximo de tipos de error a retornar.
     * @return lista de errores frecuentes.
     */
    List<ErrorFrecuente> obtenerErroresFrecuentesEstudiante(Long estudianteId, int limite);

    /**
     * Obtiene el resumen estadístico de todos los estudiantes activos de un docente.
     *
     * @param docenteId identificador del docente.
     * @return lista de resúmenes estadísticos.
     */
    List<ResumenEstadistica> obtenerResumenEstudiantesPorDocente(Long docenteId);
}