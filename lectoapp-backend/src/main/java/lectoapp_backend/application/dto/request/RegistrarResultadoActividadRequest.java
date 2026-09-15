package lectoapp_backend.application.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Solicitud para registrar el resultado obtenido por un estudiante
 * al finalizar una actividad.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarResultadoActividadRequest {

    /**
     * Actividad resuelta.
     */
    @NotNull(message = "La actividad es obligatoria.")
    private Long actividadId;

    /**
     * Tiempo empleado en segundos.
     */
    @NotNull(message = "La duración es obligatoria.")
    @Positive(message = "La duración debe ser mayor que cero.")
    private Integer duracionSegundos;

    /**
     * Respuestas enviadas por el estudiante.
     */
    @Valid
    @NotEmpty(message = "Debe existir al menos una respuesta.")
    private List<RespuestaActividadRequest> respuestas;

}