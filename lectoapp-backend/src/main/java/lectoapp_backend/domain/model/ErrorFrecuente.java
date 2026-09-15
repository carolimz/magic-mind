package lectoapp_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un tipo de error frecuente cometido por el estudiante.
 *
 * <p>
 * Sus datos son calculados a partir del campo JSONB {@code errores}
 * de la tabla {@code resultado_actividad}. No corresponde a una entidad
 * persistente individual.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorFrecuente {

    /** Tipo de error registrado en el JSONB. */
    private String tipoError;

    /** Número de veces que aparece ese tipo de error. */
    private Long frecuencia;

}