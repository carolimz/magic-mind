package lectoapp_backend.application.service.reglas;

import java.util.List;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;

/**
 * Contrato que deben implementar todas las reglas pedagógicas de una etapa.
 *
 * <p>
 * Cada implementación conoce únicamente las reglas de una etapa específica,
 * permitiendo mantener el motoe r de detección desacoplado y fácil de extender.
 * </p>
 */
public interface ReglaEtapa {

    /**
     * Retorna el identificador de la etapa que implementa esta regla.
     *
     * @return id de la etapa
     */
    Long getEtapaId();

    /**
     * Analiza las respuestas del estudiante y detecta los errores
     * correspondientes a la etapa.
     *
     * @param actividad actividad realizada
     * @param items ítems de la actividad
     * @param respuestas respuestas del estudiante
     * @return lista de errores detectados
     */
    List<ErrorDetectado> detectar(

            Actividad actividad,

            List<ItemActividad> items,

            List<RespuestaActividadRequest> respuestas);

}