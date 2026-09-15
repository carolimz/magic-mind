package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private String token;

    @Builder.Default
    private String tipo = "Bearer";

    private String nombre;

    private String apellido;

    private String correo;

    private String rol;

}