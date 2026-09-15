package lectoapp_backend.infraestructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lectoapp_backend.infraestructure.persistence.entity.EtapaEntity;

public interface JpaEtapaRepository extends JpaRepository<EtapaEntity, Long> {

    Optional<EtapaEntity> findByOrden(Integer orden);

}