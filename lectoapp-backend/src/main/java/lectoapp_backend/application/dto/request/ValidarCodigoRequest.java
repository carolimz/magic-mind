package lectoapp_backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Responsabilidad:
 * Contiene el código que el estudiante ingresa para acceder a la plataforma.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidarCodigoRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede superar los 10 caracteres")
    private String codigoAcceso;

}