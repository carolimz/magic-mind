package lectoapp_backend.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import lectoapp_backend.shared.enums.Rol;

@Getter
@Builder
@AllArgsConstructor
public class Usuario {

    private Long id;

    private String nombre;

    private String apellido;

    private String correo;

    private String password;

    private Rol rol;

    private Boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void actualizar(
            String nombre,
            String apellido,
            String correo,
            Rol rol
    ){

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;

    }
    /**
     * Activa el usuario.
     */
    public void activar() {
        this.activo = true;
    }


    /**
     * Desactiva el usuario.
     */
    public void desactivar() {
        this.activo = false;
    }
    /**
     * Actualiza la contraseña del usuario.
     */
    public void actualizarPassword(String password) {
        this.password = password;
    }

}