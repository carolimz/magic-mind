package lectoapp_backend.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {
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
