package lectoapp_backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la respuesta enviada por el estudiante
 * para un ítem de la actividad.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaActividadRequest {

    /**
     * Identificador del ítem dentro de la actividad.
     */
    @NotNull(message = "El item es obligatorio.")
    private Integer itemId;

    /**
     * Respuesta seleccionada por el estudiante.
     */
    @NotBlank(message = "La respuesta es obligatoria.")
    private String respuesta;

}