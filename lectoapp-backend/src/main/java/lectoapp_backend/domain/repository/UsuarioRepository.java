package lectoapp_backend.domain.repository;
import java.util.List;
import java.util.Optional;

import lectoapp_backend.domain.model.Usuario;

public interface UsuarioRepository {

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    Optional<Usuario> buscarPorCorreo(String correo);

    List<Usuario> listarTodos();

    void eliminar(Long id);

    boolean existeAdministrador();

}
