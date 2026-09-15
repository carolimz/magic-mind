package lectoapp_backend.application.usecase.resultadoactividad;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.request.RegistrarResultadoActividadRequest;
import lectoapp_backend.application.dto.response.ResultadoActividadResponse;
import lectoapp_backend.application.dto.response.ResultadoActividadResponseMapper;
import lectoapp_backend.application.service.MotorReglasErrores;
import lectoapp_backend.application.service.ResultadoActividadCalculator;
import lectoapp_backend.application.service.ResultadoActividadFactory;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ConfiguracionActividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.model.ResultadoCalculo;
import lectoapp_backend.domain.repository.ActividadRepository;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lectoapp_backend.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarResultadoActividadUseCase {

    private final ActividadRepository actividadRepository;

    private final ResultadoActividadRepository resultadoRepository;

    private final ResultadoActividadCalculator calculator;

    private final MotorReglasErrores motorReglas;

    private final ResultadoActividadFactory factory;

    private final ResultadoActividadResponseMapper responseMapper;

    public ResultadoActividadResponse ejecutar(

            Long estudianteId,

            RegistrarResultadoActividadRequest request) {


        if (request.getRespuestas().isEmpty()) {

                throw new RuntimeException(
                        "La actividad debe contener al menos una respuesta.");

        }

        /*
         * 1. Buscar actividad
         */
        Actividad actividad = actividadRepository

                .buscarPorId(request.getActividadId())

                .orElseThrow(() ->

                        new RuntimeException("Actividad no encontrada."));

        /*
         * 2. Leer configuración JSON
         */
        ConfiguracionActividad configuracion =

                JsonUtils.fromJson(

                        actividad.getConfiguracion(),

                        ConfiguracionActividad.class);

        /*
         * 3. Detectar errores
         */
        List<ErrorDetectado> errores =

                motorReglas.detectarErrores(

                        actividad,

                        configuracion.getItems(),

                        request.getRespuestas());

        /*
         * 4. Calcular puntaje
         */
        ResultadoCalculo calculo =

                calculator.calcular(

                        configuracion.getItems(),

                        request.getRespuestas());

        /*
         * 5. Construir resultado
         */
        ResultadoActividad resultado =

                factory.crear(

                        estudianteId,

                        actividad,

                        calculo,

                        errores,

                        request.getDuracionSegundos());

        /*
         * 6. Guardar
         */
        ResultadoActividad guardado =

                resultadoRepository.guardar(resultado);

        /*
         * 7. Responder
         */
        return responseMapper.toResponse(guardado);

    }

}