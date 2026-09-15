package lectoapp_backend.application.usecase.estudiantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.request.CrearEstudianteRequest;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.domain.service.GeneradorCodigoService;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Ejecuta el caso de uso para crear un estudiante.
 *
 * El docente ya no se recibe desde el frontend.
 * Se obtiene automáticamente del usuario autenticado mediante JWT.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CrearEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;

    private final GeneradorCodigoService generadorCodigoService;

    private final CurrentUserService currentUserService;

    public Estudiante ejecutar(CrearEstudianteRequest request) {

        // Obtiene el docente autenticado desde Spring Security.
        Usuario docente = currentUserService.obtenerUsuarioActual();

        // Genera un código de acceso único para el estudiante.
        String codigo = generadorCodigoService.generarCodigo(
                request.getNombre(),
                request.getApellido());

        // Construye el estudiante utilizando el docente autenticado.
        Estudiante estudiante = Estudiante.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .fechaNacimiento(request.getFechaNacimiento())
                .docenteId(docente.getId())
                .codigoAcceso(codigo)
                .activo(true)
                .build();

        return estudianteRepository.guardar(estudiante);

    }

}