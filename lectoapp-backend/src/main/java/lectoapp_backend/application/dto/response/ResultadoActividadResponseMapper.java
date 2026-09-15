package lectoapp_backend.application.dto.response;

import org.mapstruct.Mapper;

import lectoapp_backend.application.mapper.ErrorDetectadoResponseMapper;
import lectoapp_backend.domain.model.ResultadoActividad;

/**
 * Convierte el modelo de dominio ResultadoActividad
 * al DTO de respuesta enviado al cliente.
 */
@Mapper(
    componentModel = "spring",
    uses = ErrorDetectadoResponseMapper.class
)
public interface ResultadoActividadResponseMapper {

    ResultadoActividadResponse toResponse(ResultadoActividad resultado);
}