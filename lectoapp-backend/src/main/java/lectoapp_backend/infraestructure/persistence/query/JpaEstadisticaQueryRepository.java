package lectoapp_backend.infraestructure.persistence.query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lectoapp_backend.infraestructure.persistence.entity.ResultadoActividadEntity;
import lectoapp_backend.infraestructure.persistence.projection.ErrorFrecuenteProjection;
import lectoapp_backend.infraestructure.persistence.projection.ProgresoEtapaProjection;
import lectoapp_backend.infraestructure.persistence.projection.ResumenEstadisticaProjection;
import lectoapp_backend.infraestructure.persistence.projection.UltimaEtapaEstudianteProjection;

/**
 * Repositorio especializado para las consultas estadísticas.
 */
public interface JpaEstadisticaQueryRepository
        extends JpaRepository<ResultadoActividadEntity, Long> {

    /**
     * Obtiene el resumen estadístico general de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @return proyección con los datos estadísticos, o {@code null} si no tiene actividades.
     */
    @Query("""
        SELECT
            e.id AS estudianteId,
            CONCAT(e.nombre, ' ', e.apellido) AS nombreCompleto,
            COUNT(r.id) AS actividadesRealizadas,
            AVG(r.porcentaje) AS promedioGeneral,
            (
                SUM(r.cantidadCorrectas) * 100.0 /
                NULLIF(SUM(r.cantidadCorrectas + r.cantidadIncorrectas), 0)
            ) AS porcentajeExito,
            AVG(r.duracionSegundos) AS tiempoPromedioSegundos,
            MAX(r.fechaRealizacion) AS ultimaActividad
        FROM ResultadoActividadEntity r
        JOIN r.estudiante e
        WHERE e.id = :estudianteId
        GROUP BY e.id, e.nombre, e.apellido
        """)
    ResumenEstadisticaProjection obtenerResumenEstudiante(
            @Param("estudianteId") Long estudianteId);

    /**
     * Obtiene el progreso desglosado por etapa pedagógica para un estudiante.
     * Calcula cuántas actividades completó el estudiante en cada etapa
     * y cuántas están disponibles en total.
     *
     * @param estudianteId identificador del estudiante.
     * @return lista de proyecciones por etapa.
     */
    @Query("""
        SELECT
            et.id AS etapaId,
            et.nombre AS nombreEtapa,
            COUNT(DISTINCT r.actividad.id) AS actividadesCompletadas,
            (SELECT COUNT(a2) FROM ActividadEntity a2 WHERE a2.etapa = et AND a2.activo = true) AS totalActividades,
            COUNT(DISTINCT r.actividad.id) * 100.0 /
                NULLIF((SELECT COUNT(a2) FROM ActividadEntity a2 WHERE a2.etapa = et AND a2.activo = true), 0)
                AS porcentajeCompletado
        FROM ResultadoActividadEntity r
        JOIN r.actividad a
        JOIN a.etapa et
        WHERE r.estudiante.id = :estudianteId
        GROUP BY et.id, et.nombre, et.orden
        ORDER BY et.orden ASC
        """)
    List<ProgresoEtapaProjection> obtenerProgresoPorEtapas(
            @Param("estudianteId") Long estudianteId);

    /**
     * Consulta los tipos de errores más frecuentes de un estudiante desde el campo JSONB.
     *
     * @param estudianteId identificador del estudiante.
     * @param limit        número máximo de registros a retornar.
     * @return lista de proyecciones con el tipo de error y su frecuencia.
     */
    @Query(value = """
        SELECT error_element->>'tipo' AS tipoError, COUNT(*) AS frecuencia
        FROM resultado_actividad r,
             jsonb_array_elements(r.errores) AS error_element
        WHERE r.estudiante_id = :estudianteId
        GROUP BY error_element->>'tipo'
        ORDER BY frecuencia DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<ErrorFrecuenteProjection> obtenerErroresFrecuentesEstudiante(
            @Param("estudianteId") Long estudianteId,
            @Param("limit") int limit);

    /**
     * Obtiene el resumen estadístico de todos los estudiantes activos de un docente.
     *
     * @param docenteId identificador del docente.
     * @return lista de proyecciones con el resumen de cada estudiante.
     */
    @Query("""
        SELECT
            e.id AS estudianteId,
            CONCAT(e.nombre, ' ', e.apellido) AS nombreCompleto,
            COUNT(r.id) AS actividadesRealizadas,
            AVG(r.porcentaje) AS promedioGeneral,
            (
                SUM(r.cantidadCorrectas) * 100.0 /
                NULLIF(SUM(r.cantidadCorrectas + r.cantidadIncorrectas), 0)
            ) AS porcentajeExito,
            AVG(r.duracionSegundos) AS tiempoPromedioSegundos,
            MAX(r.fechaRealizacion) AS ultimaActividad
        FROM EstudianteEntity e
        LEFT JOIN ResultadoActividadEntity r ON r.estudiante.id = e.id
        WHERE e.docente.id = :docenteId AND e.activo = true
        GROUP BY e.id, e.nombre, e.apellido
        ORDER BY e.apellido ASC, e.nombre ASC
        """)
    List<ResumenEstadisticaProjection> obtenerResumenEstudiantesPorDocente(
            @Param("docenteId") Long docenteId);

    /**
     * Obtiene la última etapa alcanzada por cada estudiante de una lista, en una sola consulta.
     *
     * <p>
     * Utiliza {@code DISTINCT ON} de PostgreSQL para traer, por cada estudiante,
     * la etapa de su actividad más reciente. Esto elimina el problema N+1 que
     * surgía al consultar la etapa actual de cada estudiante de forma individual.
     * </p>
     *
     * @param estudianteIds lista de identificadores de estudiantes.
     * @return lista de proyecciones con el estudianteId y su etapa actual.
     */
    @Query(value = """
        SELECT DISTINCT ON (r.estudiante_id)
            r.estudiante_id     AS estudianteId,
            e.id                AS etapaId,
            e.nombre            AS nombreEtapa
        FROM resultado_actividad r
        JOIN actividad a ON r.actividad_id = a.id
        JOIN etapa e     ON a.etapa_id     = e.id
        WHERE r.estudiante_id IN (:estudianteIds)
        ORDER BY r.estudiante_id, r.fecha_realizacion DESC
        """, nativeQuery = true)
    List<UltimaEtapaEstudianteProjection> obtenerUltimaEtapaPorEstudiantes(
            @Param("estudianteIds") List<Long> estudianteIds);
}