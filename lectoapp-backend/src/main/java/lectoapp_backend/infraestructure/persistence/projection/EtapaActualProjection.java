package lectoapp_backend.infraestructure.persistence.projection;

/**
 * Proyección utilizada para obtener la etapa actual
 * de un estudiante.
 */
public interface EtapaActualProjection {

    Long getEtapaId();

    String getNombreEtapa();

}