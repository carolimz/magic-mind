package lectoapp_backend.application.dto.response;


import lectoapp_backend.shared.enums.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioResponse {

    private Long id;

    private String nombre;

    private String apellido;

    private String correo;

    private Rol rol;

    private Boolean activo;

}