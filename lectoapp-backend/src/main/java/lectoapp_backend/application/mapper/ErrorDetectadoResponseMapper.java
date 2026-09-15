package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lectoapp_backend.application.dto.response.ErrorDetectadoResponse;
import lectoapp_backend.domain.model.ErrorDetectado;

/**
 * Convierte los errores detectados del dominio
 * a su representación para la API.
 */
@Mapper(componentModel = "spring")
public interface ErrorDetectadoResponseMapper {


    @Mapping(source = "itemId", target = "preguntaId")
    ErrorDetectadoResponse toResponse(ErrorDetectado error);

}