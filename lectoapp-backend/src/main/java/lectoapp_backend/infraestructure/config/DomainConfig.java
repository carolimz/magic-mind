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
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}