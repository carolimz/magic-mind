package lectoapp_backend.application.service.reglas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.shared.enums.TipoError;

/**
 * Reglas pedagógicas correspondientes a la etapa 1.
 *
 * Esta etapa evalúa la orientación espacial del estudiante
 * mediante preguntas sobre posiciones y direcciones.
 */
@Component
public class ReglaEtapa1 extends AbstractReglaEtapa {

    @Override
    public Long getEtapaId() {

        return 1L;
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
                    esConfusionDireccional(
                            correcta,
                            estudiante)
                            ? TipoError.CONFUSION_DIRECCIONES
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
     * Determina si el estudiante confundió
     * dos direcciones espaciales opuestas.
     */
    private boolean esConfusionDireccional(
            String correcta,
            String estudiante) {

        if (correcta == null || estudiante == null) {

            return false;
        }

        String correctaNormalizada =
                correcta.trim().toUpperCase();

        String estudianteNormalizada =
                estudiante.trim().toUpperCase();

        return
                (correctaNormalizada.equals("ARRIBA")
                        && estudianteNormalizada.equals("ABAJO"))

                ||

                (correctaNormalizada.equals("ABAJO")
                        && estudianteNormalizada.equals("ARRIBA"))

                ||

                (correctaNormalizada.equals("IZQUIERDA")
                        && estudianteNormalizada.equals("DERECHA"))

                ||

                (correctaNormalizada.equals("DERECHA")
                        && estudianteNormalizada.equals("IZQUIERDA"));
    }
}