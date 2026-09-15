package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;

import lectoapp_backend.application.dto.response.EtapaResponse;
import lectoapp_backend.domain.model.Etapa;

/**
 * Responsabilidad:
 * Convierte el modelo de dominio {@link Etapa}
 * en el DTO enviado al cliente.
 */
@Mapper(componentModel = "spring")
public interface EtapaResponseMapper {

    /**
     * Convierte una etapa del dominio en su representación
     * para la capa de presentación.
     *
     * @param etapa modelo del dominio.
     * @return respuesta enviada al cliente.
     */
    EtapaResponse toResponse(Etapa etapa);

}