package lectoapp_backend.infraestructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "lectoapp.admin")
public class AdminProperties {

    private String nombre;

    private String apellido;

    private String correo;

    private String password;

}