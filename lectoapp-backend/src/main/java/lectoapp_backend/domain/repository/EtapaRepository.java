package lectoapp_backend.domain.repository;

import java.util.List;
import java.util.Optional;

import lectoapp_backend.domain.model.Etapa;

public interface EtapaRepository {

    Optional<Etapa> buscarPorId(Long id);

    Optional<Etapa> buscarPorOrden(Integer orden);

    List<Etapa> listarTodas();

}