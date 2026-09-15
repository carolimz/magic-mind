package lectoapp_backend.application.usecase.actividad;

import org.springframework.stereotype.Service;

import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.repository.ActividadRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Obtiene una actividad específica mediante su identificador.
 *
 * <p>
 * Este caso de uso permite recuperar toda la información necesaria
 * para que el frontend construya dinámicamente la actividad que
 * resolverá el estudiante.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class BuscarActividadUseCase {

    /**
     * Repositorio encargado del acceso a las actividades.
     */
    private final ActividadRepository actividadRepository;

    /**
     * Busca una actividad por su identificador.
     *
     * @param id identificador de la actividad.
     * @return actividad encontrada.
     * @throws ResourceNotFoundException si la actividad no existe.
     */
    public Actividad ejecutar(Long id) {

        return actividadRepository
                .buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La actividad solicitada no existe."));

    }

}