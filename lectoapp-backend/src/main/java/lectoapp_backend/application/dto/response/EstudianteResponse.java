package lectoapp_backend.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Responsabilidad:
 * Representa la información de un estudiante enviada al cliente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteResponse {

    private Long id;

    private String nombre;

    private String apellido;

    private String codigoAcceso;

    private LocalDate fechaNacimiento;

    private Boolean activo;

    private LocalDateTime ultimaSesion;

    private Long docenteId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}