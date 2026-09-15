package lectoapp_backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa el resumen estadístico de un estudiante.
 *
 * <p>
 * Este modelo consolida la información principal utilizada por el panel
 * del docente. Sus datos son obtenidos a partir de los resultados de las
 * actividades y no corresponden a una entidad persistente.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenEstadistica {

    private Long estudianteId;

    private String nombreCompleto;

    /**
     * Etapa actual del estudiante.
     * Se obtiene a partir de la última actividad realizada.
     */
    private Long etapaId;

    /**
     * Nombre de la etapa actual.
     */
    private String nombreEtapa;

    /**
     * Cantidad total de actividades realizadas.
     */
    private Integer actividadesRealizadas;

    /**
     * Promedio general del estudiante.
     */
    private BigDecimal promedioGeneral;

    /**
     * Porcentaje global de éxito.
     */
    private BigDecimal porcentajeExito;

    /**
     * Tiempo promedio empleado por actividad.
     */
    private Integer tiempoPromedioSegundos;

    /**
     * Fecha de la última actividad realizada.
     */
    private LocalDateTime ultimaActividad;

}