package lectoapp_backend.application.service.reglas;

import java.util.List;

import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.shared.enums.TipoError;

/**
 * Utilidades compartidas por las reglas pedagógicas.
 *
 * <p>
 * Centraliza operaciones comunes para evitar duplicar código en las
 * implementaciones de cada etapa.
 * </p>
 */
public final class ReglaUtils {

    private ReglaUtils() {
    }

    /**
     * Busca un ítem dentro de la actividad.
     *
     * @param items lista de ítems
     * @param itemId identificador buscado
     * @return ítem encontrado
     */
    public static ItemActividad buscarItem(

            List<ItemActividad> items,

            Integer itemId) {

        return items.stream()

                .filter(item -> item.getId().equals(itemId))

                .findFirst()

                .orElseThrow(() -> new IllegalArgumentException(

                        "No existe el ítem " + itemId));

    }

    /**
     * Crea un ErrorDetectado.
     */
    public static ErrorDetectado crearError(

            Integer itemId,

            TipoError tipo,

            String respuestaEsperada,

            String respuestaEstudiante) {

        return ErrorDetectado.builder()

                .itemId(itemId)

                .tipo(tipo)

                .respuestaEsperada(respuestaEsperada)

                .respuestaEstudiante(respuestaEstudiante)

                .build();

    }

    /**
     * Compara dos respuestas ignorando mayúsculas y espacios.
     */
    public static boolean sonIguales(

            String esperada,

            String estudiante) {

        if (esperada == null || estudiante == null) {

            return false;

        }

        return esperada.trim()

                .equalsIgnoreCase(estudiante.trim());

    }

}