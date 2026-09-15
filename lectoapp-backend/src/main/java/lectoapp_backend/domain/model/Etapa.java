package lectoapp_backend.domain.model;

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
public class Etapa {

    private Long id;

    private String nombre;

    private String descripcion;

    private String objetivoPedagogico;

    private Integer orden;

    private Boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}