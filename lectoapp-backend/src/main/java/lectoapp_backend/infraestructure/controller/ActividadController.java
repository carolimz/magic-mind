package lectoapp_backend.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lectoapp_backend.application.dto.response.ActividadResponse;
import lectoapp_backend.application.mapper.ActividadResponseMapper;
import lectoapp_backend.application.usecase.actividad.BuscarActividadUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Expone los endpoints relacionados con las actividades.
 */
@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
@Validated
@Tag(name = "Actividades", description = "Operaciones para consultar actividades de aprendizaje")
public class ActividadController {

    private final BuscarActividadUseCase buscarActividadUseCase;

    private final ActividadResponseMapper actividadResponseMapper;

    /**
     * Obtiene una actividad específica mediante su identificador.
     */
    @Operation(summary = "Buscar actividad", description = "Obtiene la actividad por su ID.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE','ESTUDIANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ActividadResponse> buscar(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                actividadResponseMapper.toResponse(

                        buscarActividadUseCase.ejecutar(id)

                )

        );

    }

}