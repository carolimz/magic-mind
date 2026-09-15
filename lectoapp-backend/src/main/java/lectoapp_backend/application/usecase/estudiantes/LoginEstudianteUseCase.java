package lectoapp_backend.application.usecase.estudiantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.application.dto.request.LoginEstudianteRequest;
import lectoapp_backend.application.dto.response.LoginResponse;
import lectoapp_backend.application.service.JwtService;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;
    private final JwtService jwtService;

    public LoginResponse ejecutar(LoginEstudianteRequest request) {

        Estudiante estudiante = estudianteRepository
                .buscarPorCodigoAcceso(request.getCodigo())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Código de estudiante no válido."));

        if (!Boolean.TRUE.equals(estudiante.getActivo())) {
            throw new IllegalStateException("El estudiante se encuentra inactivo.");
        }

        String token = jwtService.generarToken(estudiante);

        return LoginResponse.builder()
                .token(token)
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .rol("ESTUDIANTE")
                .build();

    }

}
