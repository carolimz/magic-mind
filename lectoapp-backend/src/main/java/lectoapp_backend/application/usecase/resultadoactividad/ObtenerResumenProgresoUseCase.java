package lectoapp_backend.application.usecase.resultadoactividad;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.ResumenProgresoResponse;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lombok.RequiredArgsConstructor;

/**
 * Caso de uso encargado de obtener el resumen general
 * del progreso de un estudiante.
 *
 * <p>
 * Este resumen será utilizado por el dashboard del estudiante,
 * el seguimiento docente y posteriormente por el módulo
 * de estadísticas.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerResumenProgresoUseCase {

    private final ResultadoActividadRepository repository;

    public ResumenProgresoResponse ejecutar(Long estudianteId) {

        Long actividadesRealizadas =
                repository.contarPorEstudiante(estudianteId);

        Integer promedioPuntaje =
                repository.promedioPuntaje(estudianteId);

        BigDecimal promedioPorcentaje =
                repository.promedioPorcentaje(estudianteId);

        ResultadoActividad ultimoResultado =
                repository.buscarUltimoResultado(estudianteId)
                        .orElse(null);

        Integer totalCorrectas = 0;
        Integer totalIncorrectas = 0;

        for (ResultadoActividad resultado :
                repository.listarPorEstudiante(estudianteId)) {

            totalCorrectas += resultado.getCantidadCorrectas();
            totalIncorrectas += resultado.getCantidadIncorrectas();

        }

        return ResumenProgresoResponse.builder()

                .actividadesRealizadas(
                        actividadesRealizadas.intValue())

                .totalCorrectas(totalCorrectas)

                .totalIncorrectas(totalIncorrectas)

                .promedioPuntaje(promedioPuntaje)

                .promedioPorcentaje(promedioPorcentaje)

                .ultimaActividad(

                        ultimoResultado != null

                                ? ultimoResultado.getFechaRealizacion()

                                : null)

                .build();

    }

}