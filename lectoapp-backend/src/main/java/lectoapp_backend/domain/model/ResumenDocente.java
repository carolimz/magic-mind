package lectoapp_backend.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de dominio que representa un resumen general del desempeño de los
 * estudiantes asociados a un docente.
 *
 * <p>
 * Este modelo consolida indicadores globales del grupo y será utilizado en el
 * panel principal del docente para ofrecer una visión rápida del estado general
 * de sus estudiantes.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenDocente {

    /**
     * Cantidad total de estudiantes asignados al docente.
     */
    private Integer totalEstudiantes;

    /**
     * Cantidad total de actividades realizadas por todos los estudiantes.
     */
    private Integer totalActividades;

    /**
     * Promedio general del grupo.
     */
    private BigDecimal promedioGrupo;

    /**
     * Tiempo promedio empleado por el grupo para completar las actividades.
     */
    private Integer tiempoPromedioSegundos;


}