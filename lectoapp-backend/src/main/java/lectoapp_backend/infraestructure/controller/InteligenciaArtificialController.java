package lectoapp_backend.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lectoapp_backend.application.dto.response.ReporteIAResponse;
import lectoapp_backend.application.usecase.ai.GenerarReporteIAEstudianteUseCase;
import lectoapp_backend.domain.model.ReporteIA;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para el módulo de Inteligencia Artificial Generativa.
 *
 * <p>
 * Expone los endpoints que permiten a docentes y administradores
 * generar reportes pedagógicos utilizando Gemini 1.5 Flash.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/ai")
@Tag(
    name = "Inteligencia Artificial",
    description = "Endpoints para la generación de reportes pedagógicos con IA Generativa (Gemini)"
)
public class InteligenciaArtificialController {

    private final GenerarReporteIAEstudianteUseCase generarReporteIAEstudianteUseCase;

    /**
     * Genera un reporte pedagógico con IA para un estudiante específico.
     *
     * <p>
     * El reporte incluye análisis de desempeño, focos de atención,
     * recomendaciones pedagógicas y fortalezas, generado en formato Markdown.
     * </p>
     *
     * @param estudianteId identificador del estudiante.
     * @return reporte generado en formato Markdown.
     */
    @Operation(
        summary = "Generar reporte pedagógico con IA",
        description = "Analiza el historial del estudiante con Gemini 1.5 Flash y genera un reporte "
                + "con análisis de desempeño, focos de atención y recomendaciones para el docente."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/reporte/estudiantes/{estudianteId}")
    public ResponseEntity<ReporteIAResponse> generarReporte(
            @PathVariable Long estudianteId) {

        ReporteIA reporte = generarReporteIAEstudianteUseCase.ejecutar(estudianteId);

        ReporteIAResponse response = ReporteIAResponse.builder()
                .estudianteId(reporte.getEstudianteId())
                .nombreEstudiante(reporte.getNombreEstudiante())
                .contenidoMarkdown(reporte.getContenidoMarkdown())
                .generadoEn(reporte.getGeneradoEn())
                .build();

        return ResponseEntity.ok(response);
    }
}
