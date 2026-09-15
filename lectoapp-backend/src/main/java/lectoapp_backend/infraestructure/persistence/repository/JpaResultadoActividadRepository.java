package lectoapp_backend.infraestructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lectoapp_backend.infraestructure.persistence.entity.ResultadoActividadEntity;

public interface JpaResultadoActividadRepository
        extends JpaRepository<ResultadoActividadEntity, Long> {

    List<ResultadoActividadEntity>
            findAllByEstudianteIdOrderByFechaRealizacionDesc(
                    Long estudianteId);

    Optional<ResultadoActividadEntity>
            findFirstByEstudianteIdOrderByFechaRealizacionDesc(
                    Long estudianteId);

    Long countByEstudianteId(Long estudianteId);

    @Query("""
            SELECT AVG(r.puntaje)
            FROM ResultadoActividadEntity r
            WHERE r.estudiante.id = :estudianteId
            """)
    Double promedioPuntaje(
            @Param("estudianteId") Long estudianteId);

    @Query("""
            SELECT AVG(r.porcentaje)
            FROM ResultadoActividadEntity r
            WHERE r.estudiante.id = :estudianteId
            """)
    BigDecimal promedioPorcentaje(
            @Param("estudianteId") Long estudianteId);

    /**
     * Retorna una sola vez cada actividad en la que el estudiante
     * haya obtenido el porcentaje mínimo requerido.
     */
    @Query("""
            SELECT DISTINCT r.actividad.id
            FROM ResultadoActividadEntity r
            WHERE r.estudiante.id = :estudianteId
              AND r.porcentaje >= :porcentajeMinimo
            """)
    List<Long> listarIdsActividadesAprobadas(
            @Param("estudianteId") Long estudianteId,
            @Param("porcentajeMinimo") BigDecimal porcentajeMinimo);
}