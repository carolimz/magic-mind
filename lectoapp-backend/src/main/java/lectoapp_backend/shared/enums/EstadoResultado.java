package lectoapp_backend.shared.enums;

/**
 * Representa el estado en el que finalizó una actividad realizada por un
 * estudiante.
 *
 * <p>
 * Este estado permite identificar si la actividad fue completada normalmente,
 * si el tiempo asignado terminó antes de finalizarla o si fue abandonada por
 * el estudiante.
 * </p>
 */
public enum EstadoResultado {

    /**
     * El estudiante completó la actividad correctamente.
     */
    COMPLETADA,

    /**
     * El tiempo límite de la actividad terminó antes de finalizarla.
     */
    TIEMPO_AGOTADO,

    /**
     * El estudiante abandonó la actividad antes de terminarla.
     */
    ABANDONADA

}