package lectoapp_backend.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa el progreso de un estudiante en una etapa pedagógica específica.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoEtapa {

    /**
     * Identificador de la etapa.
     */
    private Long etapaId;

    /**
     * Nombre de la etapa.
     */
    private String nombreEtapa;

    /**
     * Cantidad de actividades completadas en la etapa.
     */
    private Integer actividadesCompletadas;

    /**
     * Cantidad total de actividades disponibles en la etapa.
     */
    private Integer totalActividades;

    /**
     * Porcentaje de avance dentro de la etapa.
     */
    private BigDecimal porcentajeCompletado;

}