package lectoapp_backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lectoapp_backend.shared.enums.EstadoResultado;

/**
 * Representa el resultado obtenido por un estudiante al finalizar una
 * actividad.
 *
 * <p>
 * Cada ejecución de una actividad genera un nuevo resultado, permitiendo
 * conservar el historial completo del proceso de aprendizaje del estudiante.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoActividad {

    /**
     * Identificador único del resultado.
     */
    private Long id;

    /**
     * Identificador del estudiante que realizó la actividad.
     */
    private Long estudianteId;

    /**
     * Identificador de la actividad realizada.
     */
    private Long actividadId;

    /**
     * Puntaje obtenido por el estudiante.
     */
    private Integer puntaje;

    /**
     * Cantidad de respuestas correctas.
     */
    private Integer cantidadCorrectas;

    /**
     * Cantidad de respuestas incorrectas.
     */
    private Integer cantidadIncorrectas;

    /**
     * Porcentaje de aciertos obtenido.
     */
    private BigDecimal porcentaje;

    /**
     * Tiempo empleado para completar la actividad.
     */
    private Integer duracionSegundos;

    /**
     * Estado en el que finalizó la actividad.
     */
    private EstadoResultado estado;

    /**
     * Errores pedagógicos detectados durante la actividad.
     */
    private List<ErrorDetectado> errores;

    /**
     * Fecha y hora en la que el estudiante finalizó la actividad.
     */
    private LocalDateTime fechaRealizacion;

    /**
     * Fecha de creación del registro.
     */
    private LocalDateTime createdAt;

}