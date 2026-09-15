package lectoapp_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lectoapp_backend.shared.enums.TipoError;

/**
 * Representa un error identificado durante la resolución de una pregunta dentro
 * de una actividad.
 *
 * <p>
 * Esta información es generada por el motor de reglas y posteriormente
 * almacenada como parte del resultado de la actividad para apoyar el
 * seguimiento del estudiante y la generación de recomendaciones mediante IA.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetectado {

    /**
     * Identificador de la pregunta dentro de la configuración de la actividad.
     */
    private Integer itemId;

    /**
     * Tipo de error identificado.
     */
    private TipoError tipo;

    /**
     * Respuesta correcta esperada.
     */
    private String respuestaEsperada;

    /**
     * Respuesta proporcionada por el estudiante.
     */
    private String respuestaEstudiante;

}