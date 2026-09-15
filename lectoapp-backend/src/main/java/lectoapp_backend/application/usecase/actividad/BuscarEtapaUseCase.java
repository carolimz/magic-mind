package lectoapp_backend.application.usecase.actividad;

import org.springframework.stereotype.Service;

import lectoapp_backend.domain.model.Etapa;
import lectoapp_backend.domain.repository.EtapaRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Obtiene una etapa específica mediante su identificador.
 */
@Service
@RequiredArgsConstructor
public class BuscarEtapaUseCase {

    private final EtapaRepository etapaRepository;

    /**
     * Busca una etapa por su identificador.
     *
     * @param id identificador de la etapa.
     * @return etapa encontrada.
     * @throws ResourceNotFoundException si la etapa no existe.
     */
    public Etapa ejecutar(Long id) {

        return etapaRepository
                .buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La etapa solicitada no existe."));

    }

}