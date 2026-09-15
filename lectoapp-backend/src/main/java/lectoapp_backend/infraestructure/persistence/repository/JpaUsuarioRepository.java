package lectoapp_backend.infraestructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lectoapp_backend.infraestructure.persistence.entity.UsuarioEntity;
import lectoapp_backend.shared.enums.Rol;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByCorreo(String correo);
    boolean existsByRol(Rol rol);

}
