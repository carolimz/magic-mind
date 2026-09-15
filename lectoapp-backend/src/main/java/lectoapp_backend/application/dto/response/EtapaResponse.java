package lectoapp_backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la información de una etapa enviada al cliente.
 *
 * <p>
 * Contiene únicamente los datos necesarios para que el frontend muestre
 * las etapas disponibles dentro del proceso de aprendizaje.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtapaResponse {

    /**
     * Identificador único de la etapa.
     */
    private Long id;

    /**
     * Nombre de la etapa.
     */
    private String nombre;

    /**
     * Descripción breve de la etapa.
     */
    private String descripcion;

    /**
     * Orden pedagógico de la etapa.
     */
    private Integer orden;

}