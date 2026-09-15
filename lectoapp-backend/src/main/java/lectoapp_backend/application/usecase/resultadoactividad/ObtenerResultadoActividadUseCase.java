package lectoapp_backend.application.usecase.resultadoactividad;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.ResultadoActividadResponse;
import lectoapp_backend.application.dto.response.ResultadoActividadResponseMapper;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lombok.RequiredArgsConstructor;

/**
 * Obtiene el detalle de un resultado específico
 * perteneciente a un estudiante.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerResultadoActividadUseCase {

    private final ResultadoActividadRepository repository;

    private final ResultadoActividadResponseMapper mapper;

    public ResultadoActividadResponse ejecutar(

            Long resultadoId,

            Long estudianteId) {

        ResultadoActividad resultado = repository

                .buscarPorId(resultadoId)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Resultado no encontrado."));

        if (!resultado.getEstudianteId().equals(estudianteId)) {

            throw new RuntimeException(
                    "No tiene permiso para consultar este resultado.");

        }

        return mapper.toResponse(resultado);

    }

}