package lectoapp_backend.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lectoapp_backend.application.mapper.EstudianteMapper;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.infraestructure.persistence.entity.EstudianteEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EstudianteRepositoryImpl implements EstudianteRepository {

    private final JpaEstudianteRepository jpaRepository;

    private final EstudianteMapper mapper;

    @Override
    public Estudiante guardar(Estudiante estudiante) {

        EstudianteEntity entity = mapper.toEntity(estudiante);

        EstudianteEntity guardado = jpaRepository.save(entity);

        return mapper.toDomain(guardado);

    }

    @Override
    public Optional<Estudiante> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);

    }

    @Override
    public Optional<Estudiante> buscarPorCodigoAcceso(String codigoAcceso) {

        return jpaRepository.findByCodigoAcceso(codigoAcceso)
                .map(mapper::toDomain);

    }

    @Override
    public List<Estudiante> listarTodos() {

        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();

    }

    @Override
    public boolean existePorCodigoAcceso(String codigoAcceso) {

        return jpaRepository.existsByCodigoAcceso(codigoAcceso);

    }

    @Override
    public Optional<Estudiante> buscarPorIdYDocenteId(
            Long estudianteId,
            Long docenteId) {

        return jpaRepository
                .findByIdAndDocenteId(estudianteId, docenteId)
                .map(mapper::toDomain);

    }

    @Override
    public List<Estudiante> listarPorDocenteId(Long docenteId) {

        return jpaRepository
                .findAllByDocenteId(docenteId)
                .stream()
                .map(mapper::toDomain)
                .toList();

    }

}