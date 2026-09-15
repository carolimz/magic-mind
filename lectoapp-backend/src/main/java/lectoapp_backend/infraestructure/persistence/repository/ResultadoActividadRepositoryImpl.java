package lectoapp_backend.infraestructure.persistence.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

import lectoapp_backend.application.mapper.ResultadoActividadMapper;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lectoapp_backend.infraestructure.persistence.entity.ResultadoActividadEntity;
import lectoapp_backend.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ResultadoActividadRepositoryImpl
        implements ResultadoActividadRepository {

    private final JpaResultadoActividadRepository jpaRepository;

    private final ResultadoActividadMapper mapper;

    @Override
    public ResultadoActividad guardar(
            ResultadoActividad resultado) {

        ResultadoActividadEntity entity =
                mapper.toEntity(resultado);

        entity.setErrores(
                JsonUtils.toJson(resultado.getErrores()));

        ResultadoActividadEntity guardado =
                jpaRepository.save(entity);

        ResultadoActividad domain =
                mapper.toDomain(guardado);

        domain.setErrores(
                deserializarErrores(guardado.getErrores()));

        return domain;
    }

    @Override
    public Optional<ResultadoActividad> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(this::convertirADominio);
    }

    @Override
    public List<ResultadoActividad> listarPorEstudiante(
            Long estudianteId) {

        return jpaRepository
                .findAllByEstudianteIdOrderByFechaRealizacionDesc(
                        estudianteId)
                .stream()
                .map(this::convertirADominio)
                .toList();
    }

    @Override
    public Optional<ResultadoActividad> buscarUltimoResultado(
            Long estudianteId) {

        return jpaRepository
                .findFirstByEstudianteIdOrderByFechaRealizacionDesc(
                        estudianteId)
                .map(this::convertirADominio);
    }

    @Override
    public Long contarPorEstudiante(Long estudianteId) {

        return jpaRepository.countByEstudianteId(estudianteId);
    }

    @Override
    public Integer promedioPuntaje(Long estudianteId) {

        Double promedio =
                jpaRepository.promedioPuntaje(estudianteId);

        return promedio == null
                ? 0
                : promedio.intValue();
    }

    @Override
    public BigDecimal promedioPorcentaje(
            Long estudianteId) {

        BigDecimal promedio =
                jpaRepository.promedioPorcentaje(estudianteId);

        return promedio == null
                ? BigDecimal.ZERO
                : promedio;
    }

    @Override
    public Set<Long> listarIdsActividadesAprobadas(
            Long estudianteId,
            BigDecimal porcentajeMinimo) {

        return new HashSet<>(
                jpaRepository.listarIdsActividadesAprobadas(
                        estudianteId,
                        porcentajeMinimo));
    }

    private ResultadoActividad convertirADominio(
            ResultadoActividadEntity entity) {

        ResultadoActividad resultado =
                mapper.toDomain(entity);

        resultado.setErrores(
                deserializarErrores(entity.getErrores()));

        return resultado;
    }

    private List<ErrorDetectado> deserializarErrores(
            String erroresJson) {

        return JsonUtils.fromJson(
                erroresJson,
                new TypeReference<List<ErrorDetectado>>() {
                });
    }
}