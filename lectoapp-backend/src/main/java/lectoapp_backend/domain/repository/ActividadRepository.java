package lectoapp_backend.domain.repository;

import java.util.List;
import java.util.Optional;

import lectoapp_backend.domain.model.Actividad;

public interface ActividadRepository {

    Optional<Actividad> buscarPorId(Long id);

    List<Actividad> listarPorEtapa(Long etapaId);

}