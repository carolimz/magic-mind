package lectoapp_backend.infraestructure.persistence.projection;


import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Proyección utilizada para consultar la evolución cronológica
 * del rendimiento de un estudiante.
 */
public interface EvolucionRendimientoProjection {

    /**
     * Fecha de realización de la actividad.
     */
    LocalDateTime getFechaRealizacion();

    /**
     * Identificador de la actividad.
     */
    Long getActividadId();

    /**
     * Nombre de la actividad.
     */
    String getNombreActividad();

    /**
     * Identificador de la etapa.
     */
    Long getEtapaId();

    /**
     * Nombre de la etapa.
     */
    String getNombreEtapa();

    /**
     * Puntaje obtenido.
     */
    Integer getPuntaje();

    /**
     * Porcentaje obtenido.
     */
    BigDecimal getPorcentajeObtenido();

}