package lectoapp_backend.application.service;

import lectoapp_backend.domain.model.Usuario;

public interface CurrentUserService {

    /**
     * Obtiene el usuario actualmente autenticado.
     */
    Usuario obtenerUsuarioActual();

}