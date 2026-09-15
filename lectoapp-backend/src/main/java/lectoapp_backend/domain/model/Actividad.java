package lectoapp_backend.domain.model;

import java.time.LocalDateTime;
import lectoapp_backend.shared.enums.DificultadActividad;
import lectoapp_backend.shared.enums.TipoActividad;
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
public class Actividad {

    private Long id;

    private String nombre;

    private String descripcion;

    private TipoActividad tipoActividad;

    private DificultadActividad dificultad;

    private String configuracion;

    private Boolean activo;

    private Long etapaId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}