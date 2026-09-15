package lectoapp_backend.infraestructure.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lectoapp_backend.application.dto.request.LoginEstudianteRequest;
import lectoapp_backend.application.dto.request.LoginRequest;
import lectoapp_backend.application.dto.response.LoginResponse;
import lectoapp_backend.application.usecase.estudiantes.LoginEstudianteUseCase;
import lectoapp_backend.application.usecase.login.LoginUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Exponer los endpoints de autenticación.
 *
 * Recibe las credenciales del usuario o el código de estudiante
 * y delega el proceso al caso de uso correspondiente.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para iniciar sesión y autenticación JWT")
public class AuthController {

    private final LoginUseCase loginUseCase;

    private final LoginEstudianteUseCase loginEstudianteUseCase;

    /**
     * Permite iniciar sesión utilizando correo y contraseña.
     */
    @Operation(summary = "Login de usuario", description = "Inicia sesión con credenciales de usuario y devuelve token JWT.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginUseCase.ejecutar(request);

        return ResponseEntity.ok(response);

    }

    /**
     * Permite iniciar sesión como estudiante utilizando un código de acceso.
     */
    @Operation(summary = "Login de estudiante", description = "Inicia sesión como estudiante con código de acceso.")
    @PostMapping("/estudiante/login")
    public ResponseEntity<LoginResponse> loginEstudiante(
            @Valid @RequestBody LoginEstudianteRequest request) {

        LoginResponse response = loginEstudianteUseCase.ejecutar(request);

        return ResponseEntity.ok(response);

    }

}
