package lectoapp_backend.application.usecase.estudiantes;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Valida que exista un estudiante activo con el código ingresado.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidarCodigoUseCase {

    private final EstudianteRepository estudianteRepository;

    public Estudiante ejecutar(String codigo) {

        Estudiante estudiante = estudianteRepository.buscarPorCodigoAcceso(codigo)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El código ingresado no existe."));

        if (!Boolean.TRUE.equals(estudiante.getActivo())) {
            throw new IllegalStateException("El estudiante se encuentra inactivo.");
        }

        return estudiante;

    }

}