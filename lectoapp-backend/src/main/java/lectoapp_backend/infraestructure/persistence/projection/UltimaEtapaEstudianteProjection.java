package lectoapp_backend.infraestructure.persistence.projection;

/**
 * Proyección que retorna la última etapa alcanzada por cada estudiante.
 *
 * <p>
 * Utilizada por la consulta de resumen docente para obtener la etapa actual
 * de todos los estudiantes en una sola consulta, evitando el problema N+1.
 * </p>
 */
public interface UltimaEtapaEstudianteProjection {

    /** Identificador del estudiante. */
    Long getEstudianteId();

    /** Identificador de la etapa de la última actividad realizada. */
    Long getEtapaId();

    /** Nombre de la etapa de la última actividad realizada. */
    String getNombreEtapa();

}
