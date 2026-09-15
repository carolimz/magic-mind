package lectoapp_backend.application.dto.response;

import lectoapp_backend.shared.enums.DificultadActividad;
import lectoapp_backend.shared.enums.EstadoEtapaAprendizaje;
import lectoapp_backend.shared.enums.TipoActividad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtapaRutaAprendizajeResponse {

    private Long etapaId;

    private String nombre;

    private String descripcion;

    private Integer orden;

    private EstadoEtapaAprendizaje estado;

    private Long actividadId;

    private String actividadNombre;

    private TipoActividad tipoActividad;

    private DificultadActividad dificultad;
}