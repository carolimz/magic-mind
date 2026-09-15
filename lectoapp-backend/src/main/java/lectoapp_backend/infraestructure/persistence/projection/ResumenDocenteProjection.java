package lectoapp_backend.infraestructure.persistence.projection;


import java.math.BigDecimal;

/**
 * Proyección utilizada para obtener el resumen general
 * del grupo de estudiantes de un docente.
 */
public interface ResumenDocenteProjection {

    /**
     * Número total de estudiantes.
     */
    Integer getTotalEstudiantes();

    /**
     * Número total de actividades realizadas.
     */
    Integer getTotalActividades();

    /**
     * Promedio general del grupo.
     */
    BigDecimal getPromedioGrupo();

    /**
     * Tiempo promedio empleado por el grupo.
     */
    Integer getTiempoPromedioSegundos();

    /**
     * Nombre de la etapa donde se encuentra la mayor cantidad de estudiantes.
     */
    String getEtapaMasFrecuente();

}