package lectoapp_backend.infraestructure.ai;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lectoapp_backend.domain.model.ReporteIA;
import lectoapp_backend.domain.service.GeneradorReporteIAService;
import lectoapp_backend.infraestructure.ai.dto.GeminiRequest;
import lectoapp_backend.infraestructure.ai.dto.GeminiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del puerto {@link GeneradorReporteIAService} usando la API
 * REST de Google Gemini 1.5 Flash.
 *
 * <p>
 * Construye un prompt pedagógico estructurado con los datos del estudiante
 * y realiza una petición HTTP a la API de Gemini para obtener el reporte
 * generado en formato Markdown.
 * </p>
 *
 * <p>
 * Utiliza {@link RestTemplate} directamente para mantener la dependencia
 * mínima y evitar frameworks adicionales como Spring AI.
 * </p>
 */
@Slf4j
@Service
public class GeminiReporteService implements GeneradorReporteIAService {

    private static final String PARAM_API_KEY = "?key=";

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public GeminiReporteService(
            RestTemplate restTemplate,
            @Value("${gemini.api.url}") String apiUrl,
            @Value("${gemini.api.key}") String apiKey) {

        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Construye el prompt pedagógico, llama a Gemini y mapea la respuesta
     * al modelo de dominio {@link ReporteIA}.
     * </p>
     */
    @Override
    public ReporteIA generarReporte(String nombreEstudiante, String resumenEstadistico) {

        String prompt = construirPrompt(nombreEstudiante, resumenEstadistico);

        GeminiRequest request = construirRequest(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiRequest> httpEntity = new HttpEntity<>(request, headers);

        String urlConClave = apiUrl + PARAM_API_KEY + apiKey;

        log.info("Solicitando reporte IA para el estudiante: {}", nombreEstudiante);

        GeminiResponse response = restTemplate.postForObject(
                urlConClave,
                httpEntity,
                GeminiResponse.class);

        String contenido = (response != null) ? response.extraerTexto() : null;

        if (contenido == null || contenido.isBlank()) {
            log.warn("Gemini devolvió una respuesta vacía para el estudiante: {}", nombreEstudiante);
            contenido = "No fue posible generar el reporte en este momento. Por favor, intente nuevamente.";
        }

        log.info("Reporte IA generado exitosamente para: {}", nombreEstudiante);

        return ReporteIA.builder()
                .nombreEstudiante(nombreEstudiante)
                .contenidoMarkdown(contenido)
                .generadoEn(LocalDateTime.now())
                .build();
    }

    /**
     * Construye el prompt del sistema que guía a Gemini para generar
     * un reporte pedagógico estructurado y accionable para el docente.
     *
     * @param nombreEstudiante nombre del estudiante.
     * @param resumenEstadistico estadísticas formateadas del estudiante.
     * @return prompt completo listo para enviar a Gemini.
     */
    private String construirPrompt(String nombreEstudiante, String resumenEstadistico) {

        return """
                Eres un Especialista Senior en Neuropsicología Infantil y Didáctica de la Lectoescritura
                de la plataforma educativa Magic Mind. Tu labor es analizar rigurosamente el historial
                de desempeño del estudiante y emitir un Informe Psicopedagógico de alto nivel profesional,
                claro, humano y altamente accionable para su docente titular.

                ## Datos del Estudiante (Plataforma Magic Mind)
                **Estudiante:** %s

                %s

                ## Directrices de Redacción del Informe
                Redacta un informe psicopedagógico exhaustivo en español usando Markdown pulcro y elegante.
                Evita generalidades o frases vacías. Fundamenta cada análisis en los datos reales del estudiante.
                Si el estudiante presenta un rendimiento excelente o pocos errores, orienta el informe hacia
                el enriquecimiento y consolidación del hábito lector. Si presenta dificultades, aborda las
                causas neurocognitivas con empatía pedagógica.

                Estructura el informe exactamente con las siguientes 5 secciones:

                ### 📋 Diagnóstico del Estadio Lector y Desempeño Global
                Identifica en qué fase del desarrollo lectoescritor se encuentra el estudiante (etapa logográfica,
                alfabética u ortográfica). Sintetiza su nivel de precisión, autonomía y consistencia a lo largo
                de sus actividades realizadas en Magic Mind.

                ### 🧠 Evaluación de Procesos Cognitivos y Lingüísticos
                Analiza las competencias demostradas en:
                - **Conciencia Fonológica y Discriminación:** Percepción y manipulación de sonidos y fonemas.
                - **Decodificación y Ensamblaje Silábico:** Precisión en sílabas simples, complejas y palabras.
                - **Comprensión y Secuencia Narrativa:** Habilidad para retener y organizar ideas del texto.

                ### 🎯 Análisis de Errores y Focos de Atención Prioritaria
                Desglosa pedagógicamente los errores más frecuentes detectados. Explica la posible causa
                cognitiva (ej. memoria de trabajo fonológica, atención dividida, confusión visoespacial de grafemas
                similares) y por qué ocurren en este punto de su desarrollo.

                ### 🏫 Plan de Acción e Intervención en el Aula
                Propone entre 3 y 4 estrategias pedagógicas específicas, lúdicas y multisensoriales que el docente
                pueda implementar en clase durante las próximas semanas (ej. ejercicios fonoarticulatorios,
                asociación grafema-objeto, lectura dialógica, modelado fonético).

                ### 🏡 Pautas de Acompañamiento Familiar y Meta de Avance
                - **Recomendaciones para el Hogar:** 2 o 3 sugerencias sencillas y motivantes para compartir
                  con los padres o cuidadores, reforzando la autoestima del niño.
                - **Meta a Corto Plazo (Magic Mind):** Un objetivo concreto y medible para alcanzar en las
                  próximas etapas de la plataforma.
                """.formatted(nombreEstudiante, resumenEstadistico);
    }

    /**
     * Construye el objeto de petición con la estructura requerida por la API de Gemini.
     *
     * @param prompt texto del prompt a enviar.
     * @return objeto {@link GeminiRequest} listo para serializar.
     */
    private GeminiRequest construirRequest(String prompt) {

        GeminiRequest.Part part = GeminiRequest.Part.builder()
                .text(prompt)
                .build();

        GeminiRequest.Content content = GeminiRequest.Content.builder()
                .parts(List.of(part))
                .build();

        return GeminiRequest.builder()
                .contents(List.of(content))
                .build();
    }
}
