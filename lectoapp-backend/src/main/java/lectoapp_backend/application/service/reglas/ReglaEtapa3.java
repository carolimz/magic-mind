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
 * Reglas pedagógicas correspondientes a la etapa 3.
 *
 * Esta etapa evalúa el reconocimiento de consonantes
 * e identifica las confusiones más frecuentes entre ellas.
 */
@Component
public class ReglaEtapa3 extends AbstractReglaEtapa {

    /**
     * Catálogo de confusiones frecuentes
     * entre consonantes.
     */
    private static final Map<String, Set<String>> CONFUSIONES =
            Map.of(
                    "B", Set.of("D"),
                    "D", Set.of("B"),
                    "M", Set.of("N"),
                    "N", Set.of("M"),
                    "P", Set.of("Q"),
                    "Q", Set.of("P")
            );

    @Override
    public Long getEtapaId() {

        return 3L;
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

            TipoError tipo = detectarTipoConfusion(
                    correcta,
                    estudiante);

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
     * Determina el tipo de confusión detectada.
     *
     * Si la respuesta es incorrecta, pero no coincide
     * con una pareja conocida, se clasifica como OTRO.
     */
    private TipoError detectarTipoConfusion(
            String correcta,
            String estudiante) {

        if (correcta == null || estudiante == null) {

            return TipoError.OTRO;
        }

        String correctaNormalizada =
                correcta.trim().toUpperCase();

        String estudianteNormalizada =
                estudiante.trim().toUpperCase();

        boolean confusionConocida =
                CONFUSIONES
                        .getOrDefault(
                                correctaNormalizada,
                                Set.of())
                        .contains(estudianteNormalizada);

        if (!confusionConocida) {

            return TipoError.OTRO;
        }

        if (esPareja(
                correctaNormalizada,
                estudianteNormalizada,
                "B",
                "D")) {

            return TipoError.CONFUSION_B_D;
        }

        if (esPareja(
                correctaNormalizada,
                estudianteNormalizada,
                "M",
                "N")) {

            return TipoError.CONFUSION_M_N;
        }

        if (esPareja(
                correctaNormalizada,
                estudianteNormalizada,
                "P",
                "Q")) {

            return TipoError.CONFUSION_P_Q;
        }

        return TipoError.OTRO;
    }

    /**
     * Verifica si dos letras pertenecen
     * a la misma pareja de confusión.
     */
    private boolean esPareja(
            String primera,
            String segunda,
            String opcion1,
            String opcion2) {

        return
                (primera.equals(opcion1)
                        && segunda.equals(opcion2))

                ||

                (primera.equals(opcion2)
                        && segunda.equals(opcion1));
    }
}