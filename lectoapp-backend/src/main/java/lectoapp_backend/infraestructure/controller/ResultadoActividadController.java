package lectoapp_backend.infraestructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lectoapp_backend.application.dto.request.RegistrarResultadoActividadRequest;
import lectoapp_backend.application.dto.response.ResultadoActividadResponse;
import lectoapp_backend.application.dto.response.ResumenProgresoResponse;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerHistorialResultadosUseCase;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerResultadoActividadUseCase;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerResultadosEstudianteUseCase;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerResumenEstudianteUseCase;
import lectoapp_backend.application.usecase.resultadoactividad.ObtenerResumenProgresoUseCase;
import lectoapp_backend.application.usecase.resultadoactividad.RegistrarResultadoActividadUseCase;
import lectoapp_backend.infraestructure.security.SecurityStudent;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
@Tag(
    name = "ResultadoActividad",
    description = "Endpoints para registrar y consultar resultados de actividades"
)
public class ResultadoActividadController {

    private final RegistrarResultadoActividadUseCase
            registrarResultadoActividadUseCase;

    private final ObtenerHistorialResultadosUseCase
            obtenerHistorialResultadosUseCase;

    private final ObtenerResultadoActividadUseCase
            obtenerResultadoActividadUseCase;

    private final ObtenerResumenProgresoUseCase
            obtenerResumenProgresoUseCase;

    private final ObtenerResultadosEstudianteUseCase
            obtenerResultadosEstudianteUseCase;

    private final ObtenerResumenEstudianteUseCase
            obtenerResumenEstudianteUseCase;

    /**
     * Registra el resultado de una actividad para el estudiante autenticado.
     */
    @Operation(
        summary = "Registrar resultado",
        description = "Registra el resultado de una actividad para el estudiante autenticado."
    )
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @PostMapping("/estudiante/actividades/resultados")
    public ResponseEntity<ResultadoActividadResponse> registrarResultado(
            Authentication authentication,
            @Valid
            @RequestBody RegistrarResultadoActividadRequest request) {

        SecurityStudent student =
                (SecurityStudent) authentication.getPrincipal();

        ResultadoActividadResponse response =
                registrarResultadoActividadUseCase.ejecutar(
                        student.getEstudiante().getId(),
                        request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Obtiene el historial del estudiante autenticado.
     */
    @Operation(
        summary = "Historial de resultados",
        description = "Obtiene el historial de resultados del estudiante autenticado."
    )
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/estudiante/resultados")
    public ResponseEntity<List<ResultadoActividadResponse>> historial(
            Authentication authentication) {

        SecurityStudent student =
                (SecurityStudent) authentication.getPrincipal();

        return ResponseEntity.ok(
                obtenerHistorialResultadosUseCase.ejecutar(
                        student.getEstudiante().getId()));
    }

    /**
     * Obtiene el detalle de un resultado del estudiante autenticado.
     */
    @Operation(
        summary = "Detalle de resultado",
        description = "Obtiene el detalle de un resultado específico del estudiante autenticado."
    )
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/estudiante/resultados/{id:\\d+}")
    public ResponseEntity<ResultadoActividadResponse> detalle(
            Authentication authentication,
            @PathVariable Long id) {

        SecurityStudent student =
                (SecurityStudent) authentication.getPrincipal();

        return ResponseEntity.ok(
                obtenerResultadoActividadUseCase.ejecutar(
                        id,
                        student.getEstudiante().getId()));
    }

    /**
     * Obtiene el resumen de progreso del estudiante autenticado.
     */
    @Operation(
        summary = "Resumen del progreso",
        description = "Obtiene un resumen del progreso del estudiante autenticado."
    )
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/estudiante/resultados/resumen")
    public ResponseEntity<ResumenProgresoResponse> resumen(
            Authentication authentication) {

        SecurityStudent student =
                (SecurityStudent) authentication.getPrincipal();

        return ResponseEntity.ok(
                obtenerResumenProgresoUseCase.ejecutar(
                        student.getEstudiante().getId()));
    }

    /**
     * Obtiene el historial de un estudiante para docentes y administradores.
     */
    @Operation(
        summary = "Resultados de estudiante",
        description = "Obtiene el historial de resultados de un estudiante para docentes y administradores."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/docente/estudiantes/{id}/resultados")
    public ResponseEntity<List<ResultadoActividadResponse>>
            resultadosEstudiante(@PathVariable Long id) {

        return ResponseEntity.ok(
                obtenerResultadosEstudianteUseCase.ejecutar(id));
    }

    /**
     * Obtiene el resumen de un estudiante para docentes y administradores.
     */
    @Operation(
        summary = "Resumen de estudiante",
        description = "Obtiene el resumen de progreso de un estudiante para docentes y administradores."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/docente/estudiantes/{id}/resumen")
    public ResponseEntity<ResumenProgresoResponse> resumenEstudiante(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                obtenerResumenEstudianteUseCase.ejecutar(id));
    }
}