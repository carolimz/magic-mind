package lectoapp_backend.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un ítem incluido dentro de la configuración
 * JSON de una actividad.
 *
 * <p>
 * Los atributos utilizados dependen del tipo de actividad:
 * selección, formación de sílabas u otras modalidades.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemActividad {

    /**
     * Identificador del ítem dentro de la actividad.
     */
    private Integer id;

    /**
     * Pregunta mostrada al estudiante en actividades
     * de selección.
     */
    private String pregunta;

    /**
     * Texto breve utilizado en actividades
     * de comprensión lectora.
     */
    private String texto;

    /**
     * Recurso visual, textual o multimedia asociado.
     */
    private String recurso;

    /**
     * Opciones disponibles en actividades de selección.
     */
    private List<String> opciones;

    /**
     * Letras utilizadas para formar una sílaba o palabra
     * en actividades de arrastrar.
     */
    private List<String> letras;

    /**
     * Respuesta correcta esperada.
     * {@code null} en actividades de tipo EMPAREJAR donde se usan
     * {@link #par1} y {@link #par2}.
     */
    private String respuestaCorrecta;

    /**
     * Primer elemento del par en actividades de memoria (EMPAREJAR).
     * Ejemplo: la vocal mayúscula "A".
     */
    private String par1;

    /**
     * Segundo elemento del par en actividades de memoria (EMPAREJAR).
     * Ejemplo: la vocal minúscula "a" o un emoji "🌽".
     */
    private String par2;

    /**
     * Tipo de emparejamiento: {@code LETRA_LETRA} o {@code LETRA_IMAGEN}.
     * Solo aplica en actividades de tipo EMPAREJAR.
     */
    private String tipo;

    /**
     * Afirmación o texto para validar en juegos de VERDADERO_FALSO.
     */
    private String afirmacion;

    /**
     * Define si la afirmación es verdadera (true) o falsa (false).
     * Solo aplica en actividades de VERDADERO_FALSO.
     */
    private Boolean esVerdadero;
}