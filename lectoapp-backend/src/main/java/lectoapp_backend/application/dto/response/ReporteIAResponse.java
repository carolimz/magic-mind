package lectoapp_backend.application.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta HTTP con el reporte pedagógico generado por la IA.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteIAResponse {

    /** Identificador del estudiante analizado. */
    private Long estudianteId;

    /** Nombre completo del estudiante analizado. */
    private String nombreEstudiante;

    /**
     * Contenido del reporte en formato Markdown.
     * El frontend es responsable de renderizarlo correctamente.
     */
    private String contenidoMarkdown;

    /** Fecha y hora en que se generó el reporte. */
    private LocalDateTime generadoEn;

}
