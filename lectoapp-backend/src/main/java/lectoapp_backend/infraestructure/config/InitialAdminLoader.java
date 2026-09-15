package lectoapp_backend.infraestructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lectoapp_backend.application.service.PasswordEncoderService;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lectoapp_backend.infraestructure.properties.AdminProperties;
import lectoapp_backend.shared.enums.Rol;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitialAdminLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoderService passwordEncoderService;

    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {

        if (usuarioRepository.existeAdministrador()) {

            System.out.println("Administrador inicial encontrado.");

            return;

        }

        Usuario admin = Usuario.builder()

                .nombre(adminProperties.getNombre())
                .apellido(adminProperties.getApellido())
                .correo(adminProperties.getCorreo())
                .password(
                        passwordEncoderService.encriptar(
                                adminProperties.getPassword()))
                .rol(Rol.ADMIN)
                .activo(true)
                .build();

        usuarioRepository.guardar(admin);

        System.out.println("===========================================");
        System.out.println("ADMINISTRADOR INICIAL CREADO");
        System.out.println("Correo : " + adminProperties.getCorreo());
        System.out.println("Password : " + adminProperties.getPassword());
        System.out.println("===========================================");

    }

}