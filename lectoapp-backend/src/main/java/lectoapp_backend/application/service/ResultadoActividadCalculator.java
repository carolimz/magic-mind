package lectoapp_backend.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.domain.model.ResultadoCalculo;

/**
 * Calcula las estadísticas generales obtenidas por el estudiante
 * al finalizar una actividad.
 */
@Service
public class ResultadoActividadCalculator {

    public ResultadoCalculo calcular(

            List<ItemActividad> items,

            List<RespuestaActividadRequest> respuestas) {

        int correctas = 0;

        int incorrectas = 0;

        for (RespuestaActividadRequest respuesta : respuestas) {

            ItemActividad item = items.stream()

                    .filter(i -> i.getId().equals(respuesta.getItemId()))

                    .findFirst()

                    .orElseThrow(() -> new IllegalArgumentException(

                            "No existe el ítem con id "
                                    + respuesta.getItemId()));

            if (esRespuestaCorrecta(item, respuesta.getRespuesta())) {

                correctas++;

            } else {

                incorrectas++;

            }

        }

        BigDecimal porcentaje =

                BigDecimal.valueOf(correctas)

                        .multiply(BigDecimal.valueOf(100))

                        .divide(

                                BigDecimal.valueOf(items.size()),

                                2,

                                RoundingMode.HALF_UP);

        return ResultadoCalculo.builder()

                .cantidadCorrectas(correctas)

                .cantidadIncorrectas(incorrectas)

                .puntaje(porcentaje.intValue())

                .porcentaje(porcentaje)

                .build();

    }

    /**
     * Determina si la respuesta del estudiante es correcta.
     *
     * <p>
     * Soporta dos modalidades:
     * <ul>
     *   <li>Ítems tradicionales: compara contra {@code respuestaCorrecta}.</li>
     *   <li>Ítems de memoria (EMPAREJAR): compara contra {@code par1}
     *       o {@code par2}, ya que el frontend envía uno de los dos
     *       valores del par como respuesta.</li>
     * </ul>
     * </p>
     */
    private boolean esRespuestaCorrecta(

            ItemActividad item,

            String respuestaEstudiante) {

        if (respuestaEstudiante == null) {

            return false;

        }

        // Ítem tradicional (selección, sílabas, etc.)
        if (item.getRespuestaCorrecta() != null) {

            return item.getRespuestaCorrecta()

                    .equalsIgnoreCase(respuestaEstudiante);

        }

        // Ítem de memoria (EMPAREJAR): el frontend envía par1
        if (item.getPar1() != null) {

            return item.getPar1()

                    .equalsIgnoreCase(respuestaEstudiante)

                    || (item.getPar2() != null

                    && item.getPar2()

                    .equalsIgnoreCase(respuestaEstudiante));

        }

        return false;

    }

}