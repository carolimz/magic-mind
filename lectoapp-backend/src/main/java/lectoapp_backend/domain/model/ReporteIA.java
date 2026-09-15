package lectoapp_backend.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa el reporte pedagógico generado por la IA para un estudiante.
 *
 * <p>
 * Este modelo no es persistente; contiene el texto del análisis
 * generado por Gemini y la información del estudiante analizado.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteIA {

    /**
     * Identificador del estudiante analizado.
     */
    private Long estudianteId;

    /**
     * Nombre completo del estudiante analizado.
     */
    private String nombreEstudiante;

    /**
     * Texto completo del reporte generado por Gemini en formato Markdown.
     * Incluye el análisis de desempeño, focos de atención y recomendaciones.
     */
    private String contenidoMarkdown;

    /**
     * Fecha y hora en que se generó el reporte.
     */
    private LocalDateTime generadoEn;

}
