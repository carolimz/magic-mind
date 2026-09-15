package lectoapp_backend.infraestructure.persistence.projection;


import java.math.BigDecimal;

/**
 * Proyección utilizada para consultar el progreso de un estudiante
 * por cada etapa del proceso de aprendizaje.
 */
public interface ProgresoEtapaProjection {

    /**
     * Identificador de la etapa.
     */
    Long getEtapaId();

    /**
     * Nombre de la etapa.
     */
    String getNombreEtapa();

    /**
     * Cantidad de actividades completadas.
     */
    Integer getActividadesCompletadas();

    /**
     * Cantidad total de actividades disponibles.
     */
    Integer getTotalActividades();

    /**
     * Porcentaje de avance de la etapa.
     */
    BigDecimal getPorcentajeCompletado();

}