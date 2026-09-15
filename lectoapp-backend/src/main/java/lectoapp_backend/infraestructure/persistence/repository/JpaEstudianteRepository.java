package lectoapp_backend.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lectoapp_backend.infraestructure.persistence.entity.EstudianteEntity;

public interface JpaEstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

    Optional<EstudianteEntity> findByCodigoAcceso(String codigoAcceso);

    boolean existsByCodigoAcceso(String codigoAcceso);

    // ===========================
    // Seguridad
    // ===========================

    Optional<EstudianteEntity> findByIdAndDocenteId(Long id, Long docenteId);

    List<EstudianteEntity> findAllByDocenteId(Long docenteId);

}