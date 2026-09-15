package lectoapp_backend.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.model.ResultadoCalculo;
import lectoapp_backend.shared.enums.EstadoResultado;

/**
 * Construye un ResultadoActividad listo para persistir.
 */
@Component
public class ResultadoActividadFactory {

    public ResultadoActividad crear(

            Long estudianteId,

            Actividad actividad,

            ResultadoCalculo calculo,

            List<ErrorDetectado> errores,

            Integer duracionSegundos) {

        return ResultadoActividad.builder()

                .estudianteId(estudianteId)

                .actividadId(actividad.getId())

                .puntaje(calculo.getPuntaje())

                .cantidadCorrectas(calculo.getCantidadCorrectas())

                .cantidadIncorrectas(calculo.getCantidadIncorrectas())

                .porcentaje(calculo.getPorcentaje())

                .duracionSegundos(duracionSegundos)

                // Como el estudiante terminó la actividad,
                // el estado es COMPLETADA.
                .estado(EstadoResultado.COMPLETADA)

                .errores(errores)

                .fechaRealizacion(LocalDateTime.now())

                .build();

    }

}