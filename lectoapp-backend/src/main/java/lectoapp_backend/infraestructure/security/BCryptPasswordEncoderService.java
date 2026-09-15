package lectoapp_backend.infraestructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lectoapp_backend.application.service.PasswordEncoderService;
import lombok.RequiredArgsConstructor;

/**
 * Responsabilidad:
 * Implementar el servicio de encriptación de contraseñas
 * utilizando el algoritmo BCrypt proporcionado por
 * Spring Security.
 */
@Service
@RequiredArgsConstructor
public class BCryptPasswordEncoderService implements PasswordEncoderService {

    private final BCryptPasswordEncoder passwordEncoder ;

    /**
     * Encripta una contraseña en texto plano.
     */
    @Override
    public String encriptar(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Compara una contraseña en texto plano con una
     * contraseña previamente encriptada.
     */
    @Override
    public boolean coincide(String password, String passwordEncriptado) {
        return passwordEncoder.matches(password, passwordEncriptado);
    }

}