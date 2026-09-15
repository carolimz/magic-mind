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
 * Reglas correspondientes a la etapa 5.
 *
 * <p>
 * Analiza palabras completas utilizando el analizador pedagógico para detectar
 * omisiones y sustituciones de letras.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ReglaEtapa5 extends AbstractReglaEtapa {

    private final AnalizadorPalabras analizador;

    @Override
    public Long getEtapaId() {
        return 5L;
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