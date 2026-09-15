package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lectoapp_backend.shared.enums.TipoError;

/**
 * Información de un error pedagógico detectado durante
 * la resolución de una actividad.
 *
 * <p>
 * Este DTO es enviado al frontend para mostrar
 * retroalimentación al estudiante y posteriormente
 * podrá utilizarse para generar estadísticas y
 * recomendaciones mediante IA.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetectadoResponse {

    /**
     * Identificador de la pregunta donde ocurrió el error.
     */
    private Integer preguntaId;

    /**
     * Tipo de error detectado.
     */
    private TipoError tipo;

    /**
     * Respuesta correcta.
     */
    private String respuestaEsperada;

    /**
     * Respuesta enviada por el estudiante.
     */
    private String respuestaEstudiante;

}