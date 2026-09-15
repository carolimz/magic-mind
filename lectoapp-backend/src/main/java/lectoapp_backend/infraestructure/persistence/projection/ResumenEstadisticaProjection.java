package lectoapp_backend.infraestructure.persistence.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Proyección utilizada para consultar el resumen estadístico
 * de un estudiante.
 */
public interface ResumenEstadisticaProjection {

    Long getEstudianteId();

    String getNombreCompleto();

    Integer getActividadesRealizadas();

    BigDecimal getPromedioGeneral();

    BigDecimal getPorcentajeExito();

    Integer getTiempoPromedioSegundos();

    LocalDateTime getUltimaActividad();

}