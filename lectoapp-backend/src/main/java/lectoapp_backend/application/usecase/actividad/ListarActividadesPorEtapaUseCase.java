package lectoapp_backend.application.usecase.actividad;

import java.util.List;

import org.springframework.stereotype.Service;

import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.repository.ActividadRepository;
import lectoapp_backend.domain.repository.EtapaRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Obtiene todas las actividades pertenecientes a una etapa.
 *
 * <p>
 * Antes de consultar las actividades, verifica que la etapa exista.
 * Esto evita devolver una lista vacía cuando realmente el problema
 * es que el identificador de la etapa no es válido.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ListarActividadesPorEtapaUseCase {

    /**
     * Repositorio encargado de las actividades.
     */
    private final ActividadRepository actividadRepository;

    /**
     * Repositorio encargado de las etapas.
     */
    private final EtapaRepository etapaRepository;

    /**
     * Obtiene todas las actividades de una etapa.
     *
     * @param etapaId identificador de la etapa.
     * @return lista de actividades.
     * @throws ResourceNotFoundException si la etapa no existe.
     */
    public List<Actividad> ejecutar(Long etapaId) {

        etapaRepository.buscarPorId(etapaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La etapa solicitada no existe."));

        return actividadRepository.listarPorEtapa(etapaId);

    }

}