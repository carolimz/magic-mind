package lectoapp_backend.application.usecase.resultadoactividad;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.response.ResultadoActividadResponse;
import lectoapp_backend.application.dto.response.ResultadoActividadResponseMapper;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.ResultadoActividad;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.domain.repository.ResultadoActividadRepository;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Permite a docentes y administradores consultar
 * el historial de resultados de un estudiante.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerResultadosEstudianteUseCase {

    private final ResultadoActividadRepository repository;

    private final EstudianteRepository estudianteRepository;

    private final CurrentUserService currentUserService;

    private final ResultadoActividadResponseMapper mapper;

    public List<ResultadoActividadResponse> ejecutar(Long estudianteId) {

        Usuario usuarioActual =
                currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        List<ResultadoActividad> resultados =
                repository.listarPorEstudiante(estudianteId);

        return resultados.stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validarPermiso(
            Usuario usuarioActual,
            Estudiante estudiante) {

        boolean esAdministrador =
                Rol.ADMIN.equals(usuarioActual.getRol());

        boolean esDocenteResponsable =
                Objects.equals(
                        estudiante.getDocenteId(),
                        usuarioActual.getId());

        if (!esAdministrador && !esDocenteResponsable) {

            throw new AccessDeniedException(
                    "El estudiante no pertenece al docente autenticado.");
        }
    }
}