package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;

import lectoapp_backend.application.dto.response.EstudianteResponse;
import lectoapp_backend.application.dto.response.ValidacionCodigoResponse;
import lectoapp_backend.domain.model.Estudiante;

/**
 * Responsabilidad:
 * Convierte el modelo de dominio a los Response DTO.
 * Se utiliza MapStruct para conversiones directas y métodos manuales
 * cuando la respuesta requiere lógica adicional.
 */
@Mapper(componentModel = "spring")
public interface EstudianteResponseMapper {

    /**
     * Convierte un estudiante del dominio a la respuesta general.
     */
    EstudianteResponse toResponse(Estudiante estudiante);

    /**
     * Convierte un estudiante a la respuesta de validación del código.
     * Este método es manual porque el campo "valido" no existe en el dominio
     * y "estudianteId" corresponde al atributo "id".
     */
    default ValidacionCodigoResponse toValidacionResponse(Estudiante estudiante) {

        if (estudiante == null) {
            return null;
        }

        return ValidacionCodigoResponse.builder()
                .valido(true)
                .estudianteId(estudiante.getId())
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .build();

    }

}