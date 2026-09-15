package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta HTTP con un tipo de error frecuente del estudiante.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorFrecuenteResponse {

    /** Tipo de error registrado en el JSONB. */
    private String tipoError;

    /** Número de veces que aparece ese tipo de error. */
    private Long frecuencia;

}
