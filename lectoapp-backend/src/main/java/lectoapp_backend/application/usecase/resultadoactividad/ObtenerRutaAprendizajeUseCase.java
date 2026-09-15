package lectoapp_backend.application.usecase.resultadoactividad;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.EtapaRutaAprendizajeResponse;
import lectoapp_backend.application.dto.response.RutaAprendizajeResponse;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.Etapa;
import lectoapp_backend.domain.repository.ActividadRepository;
import lectoapp_backend.domain.repository.EtapaRepository;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lectoapp_backend.shared.enums.EstadoEtapaAprendizaje;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerRutaAprendizajeUseCase {

    private static final BigDecimal PORCENTAJE_MINIMO =
            new BigDecimal("70.00");

    private final EtapaRepository etapaRepository;

    private final ActividadRepository actividadRepository;

    private final ResultadoActividadRepository
            resultadoActividadRepository;

    public RutaAprendizajeResponse ejecutar(Long estudianteId) {

        List<Etapa> etapas = etapaRepository
                .listarTodas()
                .stream()
                .sorted(Comparator.comparing(Etapa::getOrden))
                .toList();

        Set<Long> actividadesAprobadas =
                resultadoActividadRepository
                        .listarIdsActividadesAprobadas(
                                estudianteId,
                                PORCENTAJE_MINIMO);

        List<EtapaRutaAprendizajeResponse> respuestas =
                new ArrayList<>();

        boolean puedeAvanzar = true;
        int etapasCompletadas = 0;

        for (Etapa etapa : etapas) {

            Actividad actividad =
                    obtenerActividadDeEtapa(etapa.getId());

            EstadoEtapaAprendizaje estado;

            if (!puedeAvanzar) {

                estado = EstadoEtapaAprendizaje.BLOQUEADA;

            } else if (actividadesAprobadas.contains(
                    actividad.getId())) {

                estado = EstadoEtapaAprendizaje.COMPLETADA;
                etapasCompletadas++;

            } else {

                estado = EstadoEtapaAprendizaje.DISPONIBLE;
                puedeAvanzar = false;
            }

            respuestas.add(
                    construirRespuesta(
                            etapa,
                            actividad,
                            estado));
        }

        return RutaAprendizajeResponse.builder()
                .porcentajeMinimoAprobacion(
                        PORCENTAJE_MINIMO)
                .etapasCompletadas(etapasCompletadas)
                .totalEtapas(etapas.size())
                .rutaCompletada(
                        etapasCompletadas == etapas.size())
                .etapas(respuestas)
                .build();
    }

    private Actividad obtenerActividadDeEtapa(
            Long etapaId) {

        return actividadRepository
                .listarPorEtapa(etapaId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "La etapa no tiene una actividad configurada."));
    }

    private EtapaRutaAprendizajeResponse construirRespuesta(
            Etapa etapa,
            Actividad actividad,
            EstadoEtapaAprendizaje estado) {

        return EtapaRutaAprendizajeResponse.builder()
                .etapaId(etapa.getId())
                .nombre(etapa.getNombre())
                .descripcion(etapa.getDescripcion())
                .orden(etapa.getOrden())
                .estado(estado)
                .actividadId(actividad.getId())
                .actividadNombre(actividad.getNombre())
                .tipoActividad(actividad.getTipoActividad())
                .dificultad(actividad.getDificultad())
                .build();
    }
}