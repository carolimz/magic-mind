package lectoapp_backend.application.service;

import lectoapp_backend.domain.model.Actividad;
import lectoapp_backend.domain.model.ConfiguracionActividad;
import lectoapp_backend.domain.model.ItemActividad;
import lectoapp_backend.shared.utils.JsonUtils;

import org.springframework.stereotype.Service;

@Service
public class ConfiguracionActividadService {

    /**
     * Obtiene toda la configuración de la actividad.
     */
    public ConfiguracionActividad obtenerConfiguracion(
            Actividad actividad) {

        return JsonUtils.fromJson(
                actividad.getConfiguracion(),
                ConfiguracionActividad.class);

    }

    /**
     * Busca un ítem dentro de la configuración.
     */
    public ItemActividad buscarItem(
            Actividad actividad,
            Integer itemId) {

        ConfiguracionActividad configuracion =
                obtenerConfiguracion(actividad);

        return configuracion.getItems()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el ítem " + itemId));

    }

}