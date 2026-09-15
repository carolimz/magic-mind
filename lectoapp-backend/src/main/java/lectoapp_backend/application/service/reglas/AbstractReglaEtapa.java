package lectoapp_backend.application.service.reglas;

import java.util.List;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;

/**
 * Clase base para las reglas pedagógicas.
 *
 * <p>
 * Proporciona utilidades compartidas para evitar duplicar lógica entre las
 * diferentes etapas del proceso de aprendizaje.
 * </p>
 */
public abstract class AbstractReglaEtapa implements ReglaEtapa {

    /**
     * Busca un ítem por su identificador.
     */
    protected ItemActividad buscarItem(

            List<ItemActividad> items,

            Integer itemId) {

        return ReglaUtils.buscarItem(

                items,

                itemId);

    }

    /**
     * Crea un ErrorDetectado.
     */
    protected ErrorDetectado crearError(

            Integer itemId,

            lectoapp_backend.shared.enums.TipoError tipo,

            String esperada,

            String estudiante) {

        return ReglaUtils.crearError(

                itemId,

                tipo,

                esperada,

                estudiante);

    }

    /**
     * Compara dos respuestas ignorando espacios y mayúsculas.
     */
    protected boolean sonIguales(

            String esperada,

            String estudiante) {

        return ReglaUtils.sonIguales(

                esperada,

                estudiante);

    }

}