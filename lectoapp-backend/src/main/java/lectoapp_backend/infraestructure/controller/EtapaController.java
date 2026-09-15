package lectoapp_backend.infraestructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lectoapp_backend.application.dto.response.ActividadResponse;
import lectoapp_backend.application.dto.response.EtapaResponse;
import lectoapp_backend.application.mapper.ActividadResponseMapper;
import lectoapp_backend.application.mapper.EtapaResponseMapper;
import lectoapp_backend.application.usecase.actividad.BuscarEtapaUseCase;
import lectoapp_backend.application.usecase.actividad.ListarActividadesPorEtapaUseCase;
import lectoapp_backend.application.usecase.actividad.ListarEtapasUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Expone los endpoints relacionados con las etapas del proceso de aprendizaje.
 */
@RestController
@RequestMapping("/api/etapas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Etapas", description = "Operaciones para gestión de etapas y actividades asociadas")
public class EtapaController {

    private final ListarEtapasUseCase listarEtapasUseCase;
    private final BuscarEtapaUseCase buscarEtapaUseCase;
    private final ListarActividadesPorEtapaUseCase listarActividadesUseCase;

    private final EtapaResponseMapper etapaResponseMapper;
    private final ActividadResponseMapper actividadResponseMapper;

    /**
     * Obtiene todas las etapas disponibles.
     */
    @Operation(summary = "Listar etapas", description = "Obtiene todas las etapas disponibles.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping
    public ResponseEntity<List<EtapaResponse>> listar() {

        return ResponseEntity.ok(

                listarEtapasUseCase.ejecutar()
                        .stream()
                        .map(etapaResponseMapper::toResponse)
                        .toList()

        );

    }

    /**
     * Obtiene una etapa por su identificador.
     */
    @Operation(summary = "Buscar etapa", description = "Obtiene una etapa por su ID.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EtapaResponse> buscar(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                etapaResponseMapper.toResponse(

                        buscarEtapaUseCase.ejecutar(id)

                )

        );

    }

    /**
     * Obtiene todas las actividades pertenecientes a una etapa.
     */
    @Operation(summary = "Listar actividades por etapa", description = "Obtiene las actividades asociadas a una etapa específica.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/{id}/actividades")
    public ResponseEntity<List<ActividadResponse>> listarActividades(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                listarActividadesUseCase.ejecutar(id)
                        .stream()
                        .map(actividadResponseMapper::toResponse)
                        .toList()

        );

    }

}