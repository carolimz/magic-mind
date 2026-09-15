package lectoapp_backend.application.service.analizador;

import org.springframework.stereotype.Component;

import lectoapp_backend.shared.enums.TipoError;

/**
 * Analiza respuestas de comprensión lectora.
 *
 * <p>
 * Actualmente identifica errores de comprensión literal comparando la
 * respuesta correcta con la seleccionada por el estudiante.
 * </p>
 */
@Component
public class AnalizadorComprension {

    public TipoError detectar(
            String correcta,
            String estudiante) {

        if (correcta == null || estudiante == null) {
            return TipoError.ERROR_COMPRENSION_LITERAL;
        }

        if (correcta.trim().equalsIgnoreCase(estudiante.trim())) {
            return null;
        }

        return TipoError.ERROR_COMPRENSION_LITERAL;
    }

}