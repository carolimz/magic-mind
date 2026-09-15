package lectoapp_backend.domain.service;

import lectoapp_backend.domain.model.ReporteIA;

/**
 * Puerto de salida para el servicio de Inteligencia Artificial Generativa.
 *
 * <p>
 * Define el contrato que la capa de aplicación necesita para generar reportes
 * con IA, sin depender de la implementación concreta (Gemini, OpenAI, etc.).
 * La implementación real pertenece a la capa de infraestructura.
 * </p>
 */
public interface GeneradorReporteIAService {

    /**
     * Genera un reporte pedagógico en Markdown para un estudiante.
     *
     * @param nombreEstudiante nombre completo del estudiante.
     * @param resumenEstadistico texto formateado con las estadísticas del estudiante.
     * @return reporte generado con el contenido en Markdown.
     */
    ReporteIA generarReporte(String nombreEstudiante, String resumenEstadistico);

}
