package lectoapp_backend.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa el resumen general del progreso
 * de un estudiante.
 *
 * <p>
 * Esta información es utilizada por el dashboard del estudiante,
 * el seguimiento docente y posteriormente por el módulo de
 * estadísticas e inteligencia artificial.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenProgresoResponse {

    /**
     * Cantidad total de actividades realizadas.
     */
    private Integer actividadesRealizadas;

    /**
     * Total de respuestas correctas obtenidas
     * en todas las actividades.
     */
    private Integer totalCorrectas;

    /**
     * Total de respuestas incorrectas obtenidas
     * en todas las actividades.
     */
    private Integer totalIncorrectas;

    /**
     * Promedio general de porcentaje de aciertos.
     */
    private BigDecimal promedioPorcentaje;

    /**
     * Promedio general del puntaje obtenido.
     */
    private Integer promedioPuntaje;

    /**
     * Fecha de la última actividad realizada.
     */
    private LocalDateTime ultimaActividad;

}