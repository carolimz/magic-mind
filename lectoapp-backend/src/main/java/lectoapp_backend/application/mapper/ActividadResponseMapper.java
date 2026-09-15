package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lectoapp_backend.application.dto.response.ActividadResponse;
import lectoapp_backend.domain.model.Actividad;

@Mapper(componentModel = "spring")
public interface ActividadResponseMapper {

    @Mapping(
        target = "configuracion",
        expression = "java(convertirJson(actividad.getConfiguracion()))"
    )
    ActividadResponse toResponse(Actividad actividad);

    default JsonNode convertirJson(String json) {

        try {

            return new ObjectMapper().readTree(json);

        } catch (Exception e) {

            throw new RuntimeException("Error al convertir la configuración JSON.", e);

        }

    }

}