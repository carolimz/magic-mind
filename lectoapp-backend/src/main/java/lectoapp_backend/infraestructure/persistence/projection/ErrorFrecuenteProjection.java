package lectoapp_backend.infraestructure.persistence.projection;

/**
 * Proyección de un tipo de error frecuente extraído del campo JSONB.
 *
 * <p>
 * Retornada por la consulta nativa que analiza el campo {@code errores}
 * de la tabla {@code resultado_actividad}, agrupando por tipo de error
 * y contando su frecuencia.
 * </p>
 */
public interface ErrorFrecuenteProjection {

    /** Tipo de error registrado en el campo JSONB. */
    String getTipoError();

    /** Número de veces que aparece ese tipo de error. */
    Long getFrecuencia();

}
