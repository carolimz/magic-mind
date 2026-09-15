package lectoapp_backend.domain.service;

import java.text.Normalizer;

import lectoapp_backend.domain.repository.EstudianteRepository;

/**
 * Responsabilidad:
 * Genera códigos únicos de acceso para los estudiantes.
 */
public class GeneradorCodigoService {

    private final EstudianteRepository estudianteRepository;

    public GeneradorCodigoService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public String generarCodigo(String nombre, String apellido) {

        String baseCodigo = obtenerPrimerasLetras(nombre, 2)
                + obtenerPrimerasLetras(apellido, 2);

        String codigo = baseCodigo;

        int contador = 2;

        while (estudianteRepository.existePorCodigoAcceso(codigo)) {
            codigo = baseCodigo + contador;
            contador++;
        }

        return codigo;
    }

    private String obtenerPrimerasLetras(String texto, int cantidad) {

        texto = limpiarTexto(texto);

        if (texto.length() <= cantidad) {
            return texto;
        }

        return texto.substring(0, cantidad);

    }

    private String limpiarTexto(String texto) {

        texto = texto.trim().toUpperCase();

        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);

        return texto.replaceAll("\\p{M}", "");

    }

}