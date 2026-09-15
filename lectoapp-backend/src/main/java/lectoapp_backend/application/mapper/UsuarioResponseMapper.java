package lectoapp_backend.application.mapper;

import org.mapstruct.Mapper;

import lectoapp_backend.application.dto.response.UsuarioResponse;
import lectoapp_backend.domain.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioResponseMapper {

    UsuarioResponse toResponse(Usuario usuario);

}