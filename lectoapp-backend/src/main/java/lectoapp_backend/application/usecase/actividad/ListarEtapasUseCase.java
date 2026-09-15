package lectoapp_backend.application.usecase.actividad;
import java.util.List;

import org.springframework.stereotype.Service;

import lectoapp_backend.domain.model.Etapa;
import lectoapp_backend.domain.repository.EtapaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Caso de uso encargado de obtener todas las etapas disponibles
 * dentro del proceso de aprendizaje de LectoApp.
 *
 * <p>
 * Este caso de uso consulta el repositorio del dominio y devuelve
 * la lista completa de etapas activas para ser consumidas por la
 * capa de presentación.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ListarEtapasUseCase {

    /**
     * Repositorio encargado del acceso a las etapas.
     */
    private final EtapaRepository etapaRepository;

    /**
     * Obtiene todas las etapas registradas en el sistema.
     *
     * @return lista de etapas.
     */
    public List<Etapa> ejecutar() {

        return etapaRepository.listarTodas();

    }

}