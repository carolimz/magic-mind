package lectoapp_backend.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.infraestructure.persistence.entity.EstudianteEntity;
import lectoapp_backend.infraestructure.persistence.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    @Mapping(source = "docente.id", target = "docenteId")
    Estudiante toDomain(EstudianteEntity entity);

    @Mapping(source = "docenteId", target = "docente")
    EstudianteEntity toEntity(Estudiante domain);

    default UsuarioEntity map(Long id) {
        if (id == null) {
            return null;
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(id);
        return usuario;
    }

    default Long map(UsuarioEntity usuario) {
        return usuario != null ? usuario.getId() : null;
    }
}