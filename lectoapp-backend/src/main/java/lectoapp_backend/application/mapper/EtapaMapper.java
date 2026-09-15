package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;

import lectoapp_backend.domain.model.Etapa;
import lectoapp_backend.infraestructure.persistence.entity.EtapaEntity;

@Mapper(componentModel = "spring")
public interface EtapaMapper {

    Etapa toDomain(EtapaEntity entity);

}