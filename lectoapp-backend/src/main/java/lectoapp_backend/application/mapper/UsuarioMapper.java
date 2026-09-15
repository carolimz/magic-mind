package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;

import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.infraestructure.persistence.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toDomain(UsuarioEntity entity);

    UsuarioEntity toEntity(Usuario usuario);

}