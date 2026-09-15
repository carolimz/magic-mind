package lectoapp_backend.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lectoapp_backend.application.dto.response.RutaAprendizajeResponse;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerRutaAprendizajeUseCase;
import lectoapp_backend.infraestructure.security.SecurityStudent;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estudiante")
@RequiredArgsConstructor
@Tag(
    name = "Ruta de aprendizaje",
    description = "Progreso secuencial del estudiante en las nueve etapas"
)
public class RutaAprendizajeController {

    private final ObtenerRutaAprendizajeUseCase
            obtenerRutaAprendizajeUseCase;

    @Operation(
        summary = "Consultar ruta de aprendizaje",
        description = "Obtiene las etapas completadas, disponibles y bloqueadas del estudiante autenticado."
    )
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/ruta-aprendizaje")
    public ResponseEntity<RutaAprendizajeResponse>
            obtenerRuta(Authentication authentication) {

        SecurityStudent student =
                (SecurityStudent) authentication.getPrincipal();

        return ResponseEntity.ok(
                obtenerRutaAprendizajeUseCase.ejecutar(
                        student.getEstudiante().getId()));
    }
}