package lectoapp_backend.shared.enums;

/**
 * Catálogo de errores pedagógicos que el sistema puede identificar durante la
 * resolución de una actividad.
 *
 * <p>
 * Estos errores son detectados por el motor de reglas y posteriormente
 * utilizados para generar estadísticas y alimentar el módulo de inteligencia
 * artificial.
 * </p>
 */
public enum TipoError {

    /**
     * El estudiante confundió una vocal por otra.
     */
    CONFUSION_VOCAL,

    /**
     * Confusión entre las letras B y D.
     */
    CONFUSION_B_D,

    /**
     * Confusión entre las letras M y N.
     */
    CONFUSION_M_N,

    /**
     * Confusión entre las letras P y Q.
     */
    CONFUSION_P_Q,

    /**
     * Omisión de una letra durante la respuesta.
     */
    OMISION_LETRA,

    /**
     * Omisión de una sílaba.
     */
    OMISION_SILABA,

    /**
     * Inversión del orden de una sílaba.
     */
    INVERSION_SILABA,

    /**
     * Sustitución incorrecta de una consonante.
     */
    SUSTITUCION_CONSONANTE,

    /**
     * Sustitución incorrecta de una vocal.
     */
    SUSTITUCION_VOCAL,

    /**
     * Error en preguntas de comprensión literal.
     */
    ERROR_COMPRENSION_LITERAL,

    /**
     * Confusión entre direcciones espaciales.
     */
    CONFUSION_DIRECCIONES,
    
    /**
     * Error que no coincide con ninguna regla específica.
     */
    OTRO

}