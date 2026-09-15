package lectoapp_backend.application.service.reglas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.application.service.analizador.AnalizadorPalabras;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.shared.enums.TipoError;
import lombok.RequiredArgsConstructor;

/**
 * Reglas pedagógicas correspondientes a la etapa 4.
 *
 * <p>
 * Evalúa la construcción de sílabas directas y detecta errores como omisiones,
 * inversiones y sustituciones utilizando el analizador de palabras.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ReglaEtapa4 extends AbstractReglaEtapa {

    private final AnalizadorPalabras analizador;

    @Override
    public Long getEtapaId() {
        return 4L;
    }

    @Override
    public List<ErrorDetectado> detectar(
            Actividad actividad,
            List<ItemActividad> items,
            List<RespuestaActividadRequest> respuestas) {

        List<ErrorDetectado> errores = new ArrayList<>();

        for (RespuestaActividadRequest respuesta : respuestas) {

            ItemActividad item = buscarItem(items, respuesta.getItemId());

            TipoError tipo = analizador.detectar(
                    item.getRespuestaCorrecta(),
                    respuesta.getRespuesta());

            if (tipo != null) {

                errores.add(
                        crearError(
                                item.getId(),
                                tipo,
                                item.getRespuestaCorrecta(),
                                respuesta.getRespuesta()));

            }

        }

        return errores;

    }

}