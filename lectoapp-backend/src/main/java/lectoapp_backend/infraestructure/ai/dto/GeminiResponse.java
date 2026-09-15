package lectoapp_backend.infraestructure.ai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que mapea la respuesta de la API de Gemini 1.5 Flash.
 *
 * <p>
 * Estructura que devuelve Gemini:
 * <pre>
 * {
 *   "candidates": [
 *     {
 *       "content": {
 *         "parts": [{ "text": "..." }]
 *       }
 *     }
 *   ]
 * }
 * </pre>
 * </p>
 *
 * <p>
 * Se usa {@code @JsonIgnoreProperties(ignoreUnknown = true)} para tolerar
 * campos extra que Gemini pueda enviar sin romper la deserialización.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    @JsonProperty("candidates")
    private List<Candidate> candidates;

    /**
     * Retorna el texto del primer candidato generado, o null si no hay respuesta.
     */
    public String extraerTexto() {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }

        Candidate candidate = candidates.get(0);
        if (candidate.getContent() == null
                || candidate.getContent().getParts() == null
                || candidate.getContent().getParts().isEmpty()) {
            return null;
        }

        return candidate.getContent().getParts().get(0).getText();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {

        @JsonProperty("content")
        private Content content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {

        @JsonProperty("parts")
        private List<Part> parts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Part {

        @JsonProperty("text")
        private String text;
    }
}
