package lectoapp_backend.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lectoapp_backend.application.mapper.ActividadMapper;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ActividadRepositoryImpl implements ActividadRepository {

    private final JpaActividadRepository jpaRepository;

    private final ActividadMapper mapper;

    @Override
    public Optional<Actividad> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);

    }

    @Override
    public List<Actividad> listarPorEtapa(Long etapaId) {

        return jpaRepository
                .findAllByEtapaIdOrderById(etapaId)
                .stream()
                .map(mapper::toDomain)
                .toList();

    }

}