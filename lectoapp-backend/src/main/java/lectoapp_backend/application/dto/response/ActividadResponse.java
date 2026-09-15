package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.databind.JsonNode;

import lectoapp_backend.shared.enums.DificultadActividad;
import lectoapp_backend.shared.enums.TipoActividad;

/**
 * Representa la información de una actividad enviada al cliente.
 *
 * <p>
 * El frontend utiliza este DTO para construir dinámicamente la actividad
 * que resolverá el estudiante.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActividadResponse {

    /**
     * Identificador único de la actividad.
     */
    private Long id;

    /**
     * Nombre de la actividad.
     */
    private String nombre;

    /**
     * Descripción o instrucción para el estudiante.
     */
    private String descripcion;

    /**
     * Tipo de actividad que debe renderizar el frontend.
     */
    private TipoActividad tipoActividad;

    /**
     * Nivel de dificultad de la actividad.
     */
    private DificultadActividad dificultad;



    /**
     * Configuración en formato JSON necesaria para construir
     * dinámicamente la actividad.
     */
    private JsonNode configuracion;

    /**
     * Identificador de la etapa a la que pertenece.
     */
    private Long etapaId;

}