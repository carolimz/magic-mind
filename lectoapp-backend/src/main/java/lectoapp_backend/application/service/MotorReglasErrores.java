package lectoapp_backend.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lectoapp_backend.application.dto.request.RespuestaActividadRequest;
import lectoapp_backend.application.service.reglas.ReglaEtapa;
import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ErrorDetectado;
import lectoapp_backend.domain.model.ItemActividad;

/**
 * Motor encargado de seleccionar automáticamente las reglas pedagógicas
 * correspondientes a la etapa de la actividad.
 *
 * <p>
 * Este servicio no contiene reglas de negocio pedagógicas.
 * Su única responsabilidad consiste en localizar la estrategia adecuada
 * para la etapa y delegar la detección de errores.
 * </p>
 */
@Service
public class MotorReglasErrores {

    /**
     * Reglas indexadas por identificador de etapa.
     */
    private final Map<Long, ReglaEtapa> reglasPorEtapa;

    public MotorReglasErrores(List<ReglaEtapa> reglas) {

        this.reglasPorEtapa = new HashMap<>();

        for (ReglaEtapa regla : reglas) {

            if (reglasPorEtapa.containsKey(regla.getEtapaId())) {

                throw new IllegalStateException(

                        "Existe más de una regla registrada para la etapa "
                                + regla.getEtapaId());

            }

            reglasPorEtapa.put(

                    regla.getEtapaId(),

                    regla);

        }

    }

    /**
     * Ejecuta las reglas pedagógicas correspondientes a la etapa
     * de la actividad.
     *
     * @param actividad  actividad realizada
     * @param items      ítems de la actividad
     * @param respuestas respuestas enviadas por el estudiante
     * @return errores detectados
     */
    public List<ErrorDetectado> detectarErrores(

            Actividad actividad,

            List<ItemActividad> items,

            List<RespuestaActividadRequest> respuestas) {

        ReglaEtapa regla = reglasPorEtapa.get(

                actividad.getEtapaId());

        if (regla == null) {

            // Etapas sin regla específica (ej. juegos de memoria)
            // no requieren detección de errores pedagógicos.
            return List.of();

        }

        return regla.detectar(

                actividad,

                items,

                respuestas);

    }

}