package lectoapp_backend.application.dto.response;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta HTTP con el resumen estadístico de un estudiante.
 *
 * <p>
 * Este DTO representa el contrato de salida del módulo de estadísticas
 * para el resumen general de rendimiento de un estudiante.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenEstadisticoEstudianteResponse {

    /**
     * Identificador del estudiante.
     */
    private Long estudianteId;

    /**
     * Nombre completo del estudiante.
     */
    private String nombreCompleto;

    /**
     * Identificador de la etapa actual.
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
     * Promedio general obtenido por el estudiante.
     */
    private BigDecimal promedioGeneral;

    /**
     * Porcentaje global de éxito.
     */
    private BigDecimal porcentajeExito;

    /**
     * Tiempo promedio empleado por actividad, expresado en segundos.
     */
    private Integer tiempoPromedioSegundos;

    /**
     * Fecha de la última actividad realizada.
     */
    private LocalDateTime ultimaActividad;
}