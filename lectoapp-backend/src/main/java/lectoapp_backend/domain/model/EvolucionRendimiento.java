package lectoapp_backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de dominio que representa un punto en la evolución del rendimiento
 * de un estudiante a lo largo de las actividades realizadas.
 *
 * <p>
 * Cada registro corresponde a una actividad completada y permite visualizar
 * cómo ha evolucionado el desempeño del estudiante con el paso del tiempo.
 * Esta información será utilizada para construir gráficas de progreso en el
 * panel del docente.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvolucionRendimiento {

    /**
     * Fecha y hora en la que se realizó la actividad.
     */
    private LocalDateTime fechaRealizacion;

    /**
     * Identificador de la actividad realizada.
     */
    private Long actividadId;

    /**
     * Nombre de la actividad realizada.
     */
    private String nombreActividad;

    /**
     * Identificador de la etapa a la que pertenece la actividad.
     */
    private Long etapaId;

    /**
     * Nombre de la etapa correspondiente.
     */
    private String nombreEtapa;

    /**
     * Porcentaje obtenido por el estudiante en la actividad.
     */
    private BigDecimal porcentajeObtenido;

    /**
     * Puntaje obtenido en la actividad.
     */
    private Integer puntaje;

}