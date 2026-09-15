package lectoapp_backend.infraestructure.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lectoapp_backend.application.dto.request.ActualizarEstudianteRequest;
import lectoapp_backend.application.dto.request.CrearEstudianteRequest;
import lectoapp_backend.application.dto.request.ValidarCodigoRequest;
import lectoapp_backend.application.dto.response.EstudianteResponse;
import lectoapp_backend.application.dto.response.ValidacionCodigoResponse;
import lectoapp_backend.application.mapper.EstudianteResponseMapper;
import lectoapp_backend.application.usecase.estudiantes.ActualizarEstudianteUseCase;
import lectoapp_backend.application.usecase.estudiantes.BuscarEstudianteUseCase;
import lectoapp_backend.application.usecase.estudiantes.CrearEstudianteUseCase;
import lectoapp_backend.application.usecase.estudiantes.DesactivarEstudianteUseCase;
import lectoapp_backend.application.usecase.estudiantes.ListarEstudiantesUseCase;
import lectoapp_backend.application.usecase.estudiantes.ValidarCodigoUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Expone los endpoints REST relacionados con los estudiantes.
 */

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Estudiantes", description = "Operaciones para gestión y validación de estudiantes")
public class EstudianteController {

    private final CrearEstudianteUseCase crearUseCase;
    private final BuscarEstudianteUseCase buscarUseCase;
    private final ActualizarEstudianteUseCase actualizarUseCase;
    private final ListarEstudiantesUseCase listarUseCase;
    private final DesactivarEstudianteUseCase desactivarUseCase;
    private final ValidarCodigoUseCase validarCodigoUseCase;

    private final EstudianteResponseMapper responseMapper;

    @Operation(summary = "Crear estudiante", description = "Crea un nuevo estudiante en el sistema.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @PostMapping
    public ResponseEntity<EstudianteResponse> crear(
            @Valid @RequestBody CrearEstudianteRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMapper.toResponse(
                        crearUseCase.ejecutar(request)));

    }

    @Operation(summary = "Buscar estudiante", description = "Busca un estudiante por su ID.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                responseMapper.toResponse(
                        buscarUseCase.ejecutar(id)));

    }

    @Operation(summary = "Listar estudiantes", description = "Devuelve la lista de todos los estudiantes.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> listar() {

        return ResponseEntity.ok(

                listarUseCase.ejecutar()
                        .stream()
                        .map(responseMapper::toResponse)
                        .toList()

        );

    }

    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante existente.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponse> actualizar(

            @PathVariable Long id,

            @Valid @RequestBody ActualizarEstudianteRequest request

    ) {

        return ResponseEntity.ok(

                responseMapper.toResponse(

                        actualizarUseCase.ejecutar(id, request)

                )

        );

    }

    @Operation(summary = "Eliminar estudiante", description = "Desactiva un estudiante mediante su ID.")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(

            @PathVariable Long id

    ) {

        desactivarUseCase.ejecutar(id);

        return ResponseEntity.noContent().build();

    }

    
    @Operation(summary = "Validar código de estudiante", description = "Valida el código de acceso proporcionado por el estudiante.")
    @PostMapping("/validar")
    public ResponseEntity<ValidacionCodigoResponse> validarCodigo(

            @Valid @RequestBody ValidarCodigoRequest request

    ) {

        return ResponseEntity.ok(

                responseMapper.toValidacionResponse(

                        validarCodigoUseCase.ejecutar(
                                request.getCodigoAcceso())

                )

        );

    }

}