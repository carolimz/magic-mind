package lectoapp_backend.infraestructure.persistence.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import lectoapp_backend.application.mapper.UsuarioMapper;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.UsuarioRepository;
import lectoapp_backend.infraestructure.persistence.entity.UsuarioEntity;
import lectoapp_backend.shared.enums.Rol;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JpaUsuarioRepository jpaRepository;

    private final UsuarioMapper mapper;

    @Override
    public Usuario guardar(Usuario usuario) {

        UsuarioEntity entity = mapper.toEntity(usuario);

        UsuarioEntity saved = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {

        return jpaRepository.findByCorreo(correo)
                .map(mapper::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {

        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Long id) {

        jpaRepository.deleteById(id);

    }
    
    @Override
    public boolean existeAdministrador() {
        return jpaRepository.existsByRol(Rol.ADMIN);
    }

}
