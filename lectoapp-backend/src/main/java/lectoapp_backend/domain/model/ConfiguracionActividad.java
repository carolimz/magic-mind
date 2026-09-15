package lectoapp_backend.domain.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Modelo utilizado para deserializar la configuración
 * almacenada en una actividad.
 */
@Getter
@Setter
public class ConfiguracionActividad {

    private List<ItemActividad> items;

}