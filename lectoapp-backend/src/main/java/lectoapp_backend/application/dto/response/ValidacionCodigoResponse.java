package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Responsabilidad:
 * Devuelve el resultado de la validación del código del estudiante.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidacionCodigoResponse {

    private Boolean valido;

    private Long estudianteId;

    private String nombre;

    private String apellido;

}