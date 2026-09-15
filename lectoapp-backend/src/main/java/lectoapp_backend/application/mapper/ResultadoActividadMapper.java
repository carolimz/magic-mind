package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.infraestructure.persistence.entity.ActividadEntity;
import lectoapp_backend.infraestructure.persistence.entity.EstudianteEntity;
import lectoapp_backend.infraestructure.persistence.entity.ResultadoActividadEntity;

/**
 * Responsabilidad:
 * Convertir entre el modelo de dominio ResultadoActividad y la entidad JPA.
 *
 * <p>
 * La conversión del campo "errores" se realiza dentro del RepositoryImpl,
 * ya que requiere serialización y deserialización JSON.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface ResultadoActividadMapper {

    /**
     * Convierte la entidad JPA al modelo de dominio.
     */
    @Mapping(source = "estudiante.id", target = "estudianteId")
    @Mapping(source = "actividad.id", target = "actividadId")
    @Mapping(target = "errores", ignore = true)
    ResultadoActividad toDomain(ResultadoActividadEntity entity);

        /**
         * Convierte el modelo de dominio a entidad JPA.
         */
    @Mapping(
        target = "estudiante",
        expression = "java(mapEstudiante(domain.getEstudianteId()))")
    @Mapping(
        target = "actividad",
        expression = "java(mapActividad(domain.getActividadId()))")
    @Mapping(target = "errores", ignore = true)
    ResultadoActividadEntity toEntity(ResultadoActividad domain);

    /**
     * Convierte el id del estudiante en una entidad de referencia.
     */
    default EstudianteEntity mapEstudiante(Long id) {

        if (id == null) {
            return null;
        }

        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(id);

        return entity;

    }

    /**
     * Obtiene el id del estudiante.
     */
    default Long map(EstudianteEntity entity) {

        return entity != null
                ? entity.getId()
                : null;

    }

    /**
     * Convierte el id de la actividad en una entidad de referencia.
     */
    default ActividadEntity mapActividad(Long id) {

        if (id == null) {
            return null;
        }

        ActividadEntity entity = new ActividadEntity();
        entity.setId(id);

        return entity;

    }

    /**
     * Obtiene el id de la actividad.
     */
    default Long map(ActividadEntity entity) {

        return entity != null
                ? entity.getId()
                : null;

    }

}