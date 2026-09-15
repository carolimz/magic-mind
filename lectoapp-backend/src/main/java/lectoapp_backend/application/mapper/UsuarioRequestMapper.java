package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lectoapp_backend.application.dto.request.UsuarioRequest;
import lectoapp_backend.domain.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Usuario toDomain(UsuarioRequest request);

}