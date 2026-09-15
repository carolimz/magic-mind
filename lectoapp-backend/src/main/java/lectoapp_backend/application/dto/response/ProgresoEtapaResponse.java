package lectoapp_backend.application.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta HTTP con el progreso de un estudiante en una etapa específica.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoEtapaResponse {

    private Long etapaId;

    private String nombreEtapa;

    /** Cantidad de actividades completadas por el estudiante en esta etapa. */
    private Integer actividadesCompletadas;

    /** Cantidad total de actividades disponibles en esta etapa. */
    private Integer totalActividades;

    /** Porcentaje de avance (actividadesCompletadas / totalActividades * 100). */
    private BigDecimal porcentajeCompletado;

}
