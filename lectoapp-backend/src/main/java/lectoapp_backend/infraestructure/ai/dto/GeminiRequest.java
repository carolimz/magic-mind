package lectoapp_backend.infraestructure.ai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el cuerpo de la petición que se envía a la API de Gemini.
 *
 * <p>
 * Estructura requerida por la API REST de Gemini 1.5 Flash:
 * <pre>
 * {
 *   "contents": [
 *     {
 *       "parts": [{ "text": "..." }]
 *     }
 *   ]
 * }
 * </pre>
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {

    @JsonProperty("contents")
    private List<Content> contents;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {

        @JsonProperty("parts")
        private List<Part> parts;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {

        @JsonProperty("text")
        private String text;
    }
}
