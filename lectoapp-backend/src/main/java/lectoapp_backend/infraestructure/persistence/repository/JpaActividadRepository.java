package lectoapp_backend.infraestructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lectoapp_backend.infraestructure.persistence.entity.ActividadEntity;

public interface JpaActividadRepository extends JpaRepository<ActividadEntity, Long> {

    List<ActividadEntity> findAllByEtapaIdOrderById(Long etapaId);

}