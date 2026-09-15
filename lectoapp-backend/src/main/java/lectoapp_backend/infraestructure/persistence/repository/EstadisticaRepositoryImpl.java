package lectoapp_backend.infraestructure.persistence.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.mapper.EstadisticaMapper;
import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.infraestructure.persistence.projection.ErrorFrecuenteProjection;
import lectoapp_backend.infraestructure.persistence.projection.ProgresoEtapaProjection;
import lectoapp_backend.infraestructure.persistence.projection.ResumenEstadisticaProjection;
import lectoapp_backend.infraestructure.persistence.projection.UltimaEtapaEstudianteProjection;
import lectoapp_backend.infraestructure.persistence.query.JpaEstadisticaQueryRepository;

/**
 * Implementación del repositorio de estadísticas.
 *
 * <p>
 * Actúa como adaptador entre la capa de dominio y los repositorios
 * de persistencia utilizados para obtener la información estadística.
 * </p>
 *
 * <p>
 * El resumen estadístico se construye a partir de dos fuentes:
 * la consulta agregada de estadísticas y el último resultado registrado
 * del estudiante, que permite determinar su etapa actual.
 * </p>
 */
@Repository
@RequiredArgsConstructor
public class EstadisticaRepositoryImpl implements EstadisticaRepository {

    private final JpaEstadisticaQueryRepository queryRepository;

    private final EstadisticaMapper estadisticaMapper;

    /**
     * Obtiene el resumen estadístico de un estudiante.
     *
     * <p>
     * Si el estudiante aún no tiene actividades registradas, retorna un
     * {@link ResumenEstadistica} con el identificador del estudiante y valores
     * en cero, en lugar de lanzar un {@code NullPointerException}.
     * La etapa actual se resuelve en una consulta separada y eficiente.
     * </p>
     *
     * @param estudianteId identificador del estudiante.
     * @return resumen estadístico completo, nunca {@code null}.
     */
    @Override
    public ResumenEstadistica obtenerResumenEstudiante(Long estudianteId) {

        ResumenEstadisticaProjection projection =
                queryRepository.obtenerResumenEstudiante(estudianteId);

        // Estudiante sin actividades aún: devolvemos resumen vacío en lugar de NPE
        if (projection == null) {
            return ResumenEstadistica.builder()
                    .estudianteId(estudianteId)
                    .actividadesRealizadas(0)
                    .build();
        }

        ResumenEstadistica resumen = estadisticaMapper.toDomain(projection);

        // Obtener la etapa actual del estudiante usando la query de última etapa
        List<UltimaEtapaEstudianteProjection> ultimasEtapas =
                queryRepository.obtenerUltimaEtapaPorEstudiantes(
                        Collections.singletonList(estudianteId));

        if (!ultimasEtapas.isEmpty()) {
            UltimaEtapaEstudianteProjection ultimaEtapa = ultimasEtapas.get(0);
            resumen.setEtapaId(ultimaEtapa.getEtapaId());
            resumen.setNombreEtapa(ultimaEtapa.getNombreEtapa());
        }

        return resumen;
    }

    /**
     * Obtiene el progreso por etapas de un estudiante.
     */
    @Override
    public List<ProgresoEtapa> obtenerProgresoPorEtapas(Long estudianteId) {
        List<ProgresoEtapaProjection> projections =
                queryRepository.obtenerProgresoPorEtapas(estudianteId);
        return estadisticaMapper.toDomainProgresoList(projections);
    }

    /**
     * Obtiene los errores más frecuentes de un estudiante.
     */
    @Override
    public List<ErrorFrecuente> obtenerErroresFrecuentesEstudiante(Long estudianteId, int limite) {
        List<ErrorFrecuenteProjection> projections =
                queryRepository.obtenerErroresFrecuentesEstudiante(estudianteId, limite);
        return estadisticaMapper.toDomainErrorList(projections);
    }

    /**
     * Obtiene el resumen estadístico de todos los estudiantes activos de un docente.
     *
     * <p>
     * Utiliza exactamente <strong>2 consultas SQL</strong> independientemente del
     * número de estudiantes, evitando el problema N+1:
     * <ol>
     *   <li>Una consulta agregada con los indicadores de rendimiento de cada estudiante.</li>
     *   <li>Una consulta {@code DISTINCT ON} para traer la etapa actual de todos los
     *       estudiantes en una sola roundtrip a la base de datos.</li>
     * </ol>
     * Los resultados se combinan en memoria usando un {@link Map} indexado por estudianteId.
     * </p>
     *
     * @param docenteId identificador del docente.
     * @return lista de resúmenes estadísticos, vacía si el docente no tiene estudiantes.
     */
    @Override
    public List<ResumenEstadistica> obtenerResumenEstudiantesPorDocente(Long docenteId) {

        // Query 1: resumen estadístico de todos los estudiantes del docente
        List<ResumenEstadisticaProjection> projections =
                queryRepository.obtenerResumenEstudiantesPorDocente(docenteId);

        if (projections.isEmpty()) {
            return Collections.emptyList();
        }

        List<ResumenEstadistica> resumenes = estadisticaMapper.toDomainResumenList(projections);

        // Aplicar valor por defecto cuando no hay actividades
        resumenes.forEach(r -> {
            if (r.getActividadesRealizadas() == null) {
                r.setActividadesRealizadas(0);
            }
        });

        // Query 2: última etapa de TODOS los estudiantes en una sola consulta (anti N+1)
        List<Long> estudianteIds = resumenes.stream()
                .map(ResumenEstadistica::getEstudianteId)
                .collect(Collectors.toList());

        Map<Long, UltimaEtapaEstudianteProjection> etapaPorEstudiante =
                queryRepository.obtenerUltimaEtapaPorEstudiantes(estudianteIds)
                        .stream()
                        .collect(Collectors.toMap(
                                UltimaEtapaEstudianteProjection::getEstudianteId,
                                p -> p));

        // Enriquecer cada resumen con su etapa actual
        resumenes.forEach(resumen -> {
            UltimaEtapaEstudianteProjection etapa =
                    etapaPorEstudiante.get(resumen.getEstudianteId());
            if (etapa != null) {
                resumen.setEtapaId(etapa.getEtapaId());
                resumen.setNombreEtapa(etapa.getNombreEtapa());
            }
        });

        return resumenes;
    }
}