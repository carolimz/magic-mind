package lectoapp_backend.application.service.analizador;

import org.springframework.stereotype.Component;

import lectoapp_backend.shared.enums.TipoError;

/**
 * Analiza dos palabras y determina
 * el error pedagógico cometido.
 *
 * Este componente es utilizado por las etapas
 * donde el estudiante forma sílabas o palabras.
 */
@Component
public class AnalizadorPalabras {

    /**
     * Determina el error cometido.
     *
     * @param correcta respuesta correcta
     * @param estudiante respuesta del estudiante
     * @return tipo de error o null si la respuesta es correcta
     */
    public TipoError detectar(
            String correcta,
            String estudiante) {

        String correctaNormalizada =
                normalizar(correcta);

        String estudianteNormalizada =
                normalizar(estudiante);

        if (correctaNormalizada.equals(
                estudianteNormalizada)) {

            return null;
        }

        if (esOmision(
                correctaNormalizada,
                estudianteNormalizada)) {

            return TipoError.OMISION_LETRA;
        }

        if (esInversion(
                correctaNormalizada,
                estudianteNormalizada)) {

            return TipoError.INVERSION_SILABA;
        }

        if (esCambioVocal(
                correctaNormalizada,
                estudianteNormalizada)) {

            return TipoError.SUSTITUCION_VOCAL;
        }

        if (esCambioConsonante(
                correctaNormalizada,
                estudianteNormalizada)) {

            return TipoError.SUSTITUCION_CONSONANTE;
        }

        /*
         * La respuesta es incorrecta, pero no coincide
         * con una regla pedagógica más específica.
         */
        return TipoError.OTRO;
    }

    private String normalizar(String texto) {

        return texto == null
                ? ""
                : texto.trim().toUpperCase();
    }

    /**
     * Detecta si faltan caracteres.
     */
    private boolean esOmision(
            String correcta,
            String estudiante) {

        return estudiante.length()
                < correcta.length();
    }

    /**
     * Detecta una inversión completa.
     */
    private boolean esInversion(
            String correcta,
            String estudiante) {

        if (correcta.length()
                != estudiante.length()) {

            return false;
        }

        return new StringBuilder(correcta)
                .reverse()
                .toString()
                .equals(estudiante);
    }

    /**
     * Detecta el cambio de una vocal por otra.
     */
    private boolean esCambioVocal(
            String correcta,
            String estudiante) {

        if (correcta.length()
                != estudiante.length()) {

            return false;
        }

        int diferencias = 0;

        for (int i = 0;
                i < correcta.length();
                i++) {

            if (correcta.charAt(i)
                    != estudiante.charAt(i)) {

                if (esVocal(correcta.charAt(i))
                        && esVocal(
                                estudiante.charAt(i))) {

                    diferencias++;

                } else {

                    return false;
                }
            }
        }

        return diferencias == 1;
    }

    /**
     * Detecta el cambio de una consonante por otra.
     */
    private boolean esCambioConsonante(
            String correcta,
            String estudiante) {

        if (correcta.length()
                != estudiante.length()) {

            return false;
        }

        int diferencias = 0;

        for (int i = 0;
                i < correcta.length();
                i++) {

            if (correcta.charAt(i)
                    != estudiante.charAt(i)) {

                if (!esVocal(correcta.charAt(i))
                        && !esVocal(
                                estudiante.charAt(i))) {

                    diferencias++;

                } else {

                    return false;
                }
            }
        }

        return diferencias == 1;
    }

    private boolean esVocal(char letra) {

        return "AEIOU".indexOf(letra) >= 0;
    }
}