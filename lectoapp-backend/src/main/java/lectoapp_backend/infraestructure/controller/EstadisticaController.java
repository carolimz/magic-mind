package lectoapp_backend.infraestructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lectoapp_backend.application.dto.response.ErrorFrecuenteResponse;
import lectoapp_backend.application.dto.response.ProgresoEtapaResponse;
import lectoapp_backend.application.dto.response.ResumenEstadisticoEstudianteResponse;
import lectoapp_backend.application.mapper.EstadisticaMapper;
import lectoapp_backend.application.usecase.estadistica.ObtenerErroresFrecuentesEstudianteUseCase;
import lectoapp_backend.application.usecase.estadistica.ObtenerProgresoEtapasEstudianteUseCase;
import lectoapp_backend.application.usecase.estadistica.ObtenerResumenEstadisticoEstudianteUseCase;
import lectoapp_backend.application.usecase.estadistica.ObtenerResumenEstudiantesDocenteUseCase;
import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para exponer los endpoints del módulo de Estadísticas.
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/estadisticas")
@Tag(
    name = "Estadisticas",
    description = "Endpoints para consultar estadísticas e indicadores de estudiantes"
)
public class EstadisticaController {

    private final ObtenerResumenEstadisticoEstudianteUseCase obtenerResumenEstadisticoEstudianteUseCase;
    private final ObtenerProgresoEtapasEstudianteUseCase obtenerProgresoEtapasEstudianteUseCase;
    private final ObtenerErroresFrecuentesEstudianteUseCase obtenerErroresFrecuentesEstudianteUseCase;
    private final ObtenerResumenEstudiantesDocenteUseCase obtenerResumenEstudiantesDocenteUseCase;
    private final EstadisticaMapper estadisticaMapper;

    /**
     * Obtiene el resumen general estadístico de un estudiante.
     * Solo accesible por docentes y administradores.
     * 
     * @param estudianteId identificador del estudiante.
     * @return resumen estadístico.
     */
    @Operation(
        summary = "Resumen estadístico del estudiante",
        description = "Obtiene los indicadores generales de rendimiento de un estudiante. Requiere permisos de docente asociado o admin."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/estudiantes/{estudianteId}/resumen")
    public ResponseEntity<ResumenEstadisticoEstudianteResponse> obtenerResumenEstudiante(
            @PathVariable Long estudianteId) {
        
        ResumenEstadistica resumen = obtenerResumenEstadisticoEstudianteUseCase.ejecutar(estudianteId);
        
        // Manejar caso donde no hay resultados para evitar null pointers, devolviendo ceros/vacíos
        if (resumen.getActividadesRealizadas() == null) {
            resumen.setActividadesRealizadas(0);
        }
        
        return ResponseEntity.ok(estadisticaMapper.toResponse(resumen));
    }

    /**
     * Obtiene el progreso por etapas pedagógicas de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @return lista de progreso por etapa.
     */
    @Operation(
        summary = "Progreso por etapas del estudiante",
        description = "Obtiene cuántas actividades completó el estudiante en cada etapa y cuántas hay disponibles."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/estudiantes/{estudianteId}/progreso")
    public ResponseEntity<List<ProgresoEtapaResponse>> obtenerProgresoEstudiante(
            @PathVariable Long estudianteId) {
        
        List<ProgresoEtapa> progreso = obtenerProgresoEtapasEstudianteUseCase.ejecutar(estudianteId);
        return ResponseEntity.ok(estadisticaMapper.toResponseProgresoList(progreso));
    }

    /**
     * Obtiene los tipos de error más frecuentes de un estudiante.
     *
     * @param estudianteId identificador del estudiante.
     * @param limite número máximo de tipos de error a retornar (por defecto 5).
     * @return lista de errores frecuentes.
     */
    @Operation(
        summary = "Errores frecuentes del estudiante",
        description = "Obtiene los tipos de errores más recurrentes del estudiante a partir del campo JSONB."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/estudiantes/{estudianteId}/errores")
    public ResponseEntity<List<ErrorFrecuenteResponse>> obtenerErroresFrecuentesEstudiante(
            @PathVariable Long estudianteId,
            @RequestParam(defaultValue = "5") int limite) {
        
        List<ErrorFrecuente> errores = obtenerErroresFrecuentesEstudianteUseCase.ejecutar(estudianteId, limite);
        return ResponseEntity.ok(estadisticaMapper.toResponseErrorList(errores));
    }

    /**
     * Obtiene el resumen estadístico de los estudiantes del docente autenticado.
     *
     * @param docenteId (solo ADMIN) identificador de otro docente a consultar.
     * @return lista de resúmenes estadísticos por estudiante.
     */
    @Operation(
        summary = "Resumen estadístico de estudiantes por docente",
        description = "Lista el resumen estadístico de todos los estudiantes asignados al docente autenticado."
    )
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/docente/estudiantes")
    public ResponseEntity<List<ResumenEstadisticoEstudianteResponse>> obtenerResumenEstudiantesDocente(
            @RequestParam(required = false) Long docenteId) {
        
        List<ResumenEstadistica> resumenes = obtenerResumenEstudiantesDocenteUseCase.ejecutar(docenteId);
        return ResponseEntity.ok(estadisticaMapper.toResponseResumenList(resumenes));
    }
}

