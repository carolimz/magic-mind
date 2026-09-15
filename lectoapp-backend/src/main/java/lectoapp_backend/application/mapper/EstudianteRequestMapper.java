package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lectoapp_backend.application.dto.request.ActualizarEstudianteRequest;
import lectoapp_backend.domain.model.Estudiante;

/**
 * Responsabilidad:
 * Actualiza un estudiante existente con la información enviada por el cliente.
 */
@Mapper(componentModel = "spring")
public interface EstudianteRequestMapper {

    void actualizarDesdeRequest(
            ActualizarEstudianteRequest request,
            @MappingTarget Estudiante estudiante);

}