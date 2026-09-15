package lectoapp_backend.application.service.reglas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.shared.enums.TipoError;

/**
 * Reglas pedagógicas correspondientes a la etapa 2.
 *
 * Esta etapa evalúa el reconocimiento de vocales
 * e identifica las confusiones más frecuentes entre ellas.
 */
@Component
public class ReglaEtapa2 extends AbstractReglaEtapa {

    /**
     * Catálogo de confusiones frecuentes entre vocales.
     */
    private static final Map<String, Set<String>> CONFUSIONES =
            Map.of(
                    "A", Set.of("E", "O"),
                    "E", Set.of("A", "I"),
                    "I", Set.of("E"),
                    "O", Set.of("A", "U"),
                    "U", Set.of("O")
            );

    @Override
    public Long getEtapaId() {

        return 2L;
    }

    @Override
    public List<ErrorDetectado> detectar(
            Actividad actividad,
            List<ItemActividad> items,
            List<RespuestaActividadRequest> respuestas) {

        List<ErrorDetectado> errores =
                new ArrayList<>();

        for (RespuestaActividadRequest respuesta : respuestas) {

            ItemActividad item = buscarItem(
                    items,
                    respuesta.getItemId());

            String correcta =
                    item.getRespuestaCorrecta();

            String estudiante =
                    respuesta.getRespuesta();

            if (sonIguales(correcta, estudiante)) {

                continue;
            }

            TipoError tipo =
                    esConfusionVocal(
                            correcta,
                            estudiante)
                            ? TipoError.CONFUSION_VOCAL
                            : TipoError.OTRO;

            errores.add(
                    crearError(
                            item.getId(),
                            tipo,
                            correcta,
                            estudiante));
        }

        return errores;
    }

    /**
     * Determina si la respuesta corresponde
     * a una confusión frecuente entre vocales.
     */
    private boolean esConfusionVocal(
            String correcta,
            String estudiante) {

        if (correcta == null || estudiante == null) {

            return false;
        }

        String correctaNormalizada =
                correcta.trim().toUpperCase();

        String estudianteNormalizada =
                estudiante.trim().toUpperCase();

        return CONFUSIONES
                .getOrDefault(
                        correctaNormalizada,
                        Set.of())
                .contains(estudianteNormalizada);
    }
}