package lectoapp_backend.application.usecase.resultadoactividad;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.ResultadoActividadResponse;
import lectoapp_backend.application.dto.response.ResultadoActividadResponseMapper;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lombok.RequiredArgsConstructor;

/**
 * Obtiene el historial de actividades realizadas
 * por un estudiante.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerHistorialResultadosUseCase {

    private final ResultadoActividadRepository repository;

    private final ResultadoActividadResponseMapper mapper;

    public List<ResultadoActividadResponse> ejecutar(Long estudianteId) {

        List<ResultadoActividad> resultados =

                repository.listarPorEstudiante(estudianteId);

        return resultados.stream()

                .map(mapper::toResponse)

                .toList();

    }

}