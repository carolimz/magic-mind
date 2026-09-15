package lectoapp_backend.domain.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

/**
 * Resultado obtenido después de evaluar una actividad.
 *
 * <p>
 * Contiene únicamente los cálculos matemáticos realizados
 * sobre las respuestas del estudiante.
 * </p>
 */
@Getter
@Builder
public class ResultadoCalculo {

    /**
     * Cantidad de respuestas correctas.
     */
    private Integer cantidadCorrectas;

    /**
     * Cantidad de respuestas incorrectas.
     */
    private Integer cantidadIncorrectas;

    /**
     * Puntaje obtenido.
     */
    private Integer puntaje;

    /**
     * Porcentaje de aciertos.
     */
    private BigDecimal porcentaje;

}