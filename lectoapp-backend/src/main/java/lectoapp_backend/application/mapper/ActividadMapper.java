package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.infraestructure.persistence.entity.ActividadEntity;

@Mapper(componentModel = "spring")
public interface ActividadMapper {

    @Mapping(source = "etapa.id", target = "etapaId")
    Actividad toDomain(ActividadEntity entity);

}