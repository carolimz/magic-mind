package lectoapp_backend.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.domain.service.GeneradorCodigoService;


/**
 * Responsabilidad:
 * Registra los servicios del dominio y de infraestructura como Beans de Spring.
 * De esta forma el dominio permanece desacoplado del framework.
 */
@Configuration
public class DomainConfig {

    @Bean
    public GeneradorCodigoService generadorCodigoService(
            EstudianteRepository estudianteRepository) {

        return new GeneradorCodigoService(estudianteRepository);

    }

    /**
     * Registra RestTemplate como Bean para ser inyectado en los servicios
     * de infraestructura que realizan peticiones HTTP (ej. Gemini).
     * Se configura con timeouts para evitar bloqueos si la API externa no responde.
     */
    @Bean
    public RestTemplate restTemplate() {
        org.springframework.http.client.SimpleClientHttpRequestFactory factory = 
                new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 segundos para conectar
        factory.setReadTimeout(30000);    // 30 segundos para leer la respuesta
        
        return new RestTemplate(factory);
    }

}