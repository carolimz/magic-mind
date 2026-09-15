package lectoapp_backend.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lectoapp_backend.application.mapper.EtapaMapper;
import lectoapp_backend.domain.model.Etapa;
import lectoapp_backend.domain.repository.EtapaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EtapaRepositoryImpl implements EtapaRepository {

    private final JpaEtapaRepository jpaRepository;

    private final EtapaMapper mapper;

    @Override
    public Optional<Etapa> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);

    }

    @Override
    public Optional<Etapa> buscarPorOrden(Integer orden) {

        return jpaRepository.findByOrden(orden)
                .map(mapper::toDomain);

    }

    @Override
    public List<Etapa> listarTodas() {

        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();

    }

}