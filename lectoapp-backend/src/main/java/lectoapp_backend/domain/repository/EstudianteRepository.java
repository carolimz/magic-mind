package lectoapp_backend.domain.repository;

import java.util.List;
import java.util.Optional;

import lectoapp_backend.domain.model.Estudiante;

public interface EstudianteRepository {

    Estudiante guardar(Estudiante estudiante);

    Optional<Estudiante> buscarPorId(Long id);

    Optional<Estudiante> buscarPorCodigoAcceso(String codigoAcceso);

    List<Estudiante> listarTodos();

    boolean existePorCodigoAcceso(String codigoAcceso);

    // ===========================
    // Nuevos métodos para seguridad
    // ===========================

    Optional<Estudiante> buscarPorIdYDocenteId(Long estudianteId, Long docenteId);

    List<Estudiante> listarPorDocenteId(Long docenteId);

}