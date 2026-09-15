package lectoapp_backend.infraestructure.config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lectoapp_backend.infraestructure.security.JwtAuthenticationFilter;
import lectoapp_backend.shared.exception.ApiError;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource)
            throws Exception {

        http
            .cors(cors ->
                    cors.configurationSource(corsConfigurationSource))

            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                    // Swagger / OpenAPI
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/swagger-ui.html").permitAll()
                    .requestMatchers("/swagger-ui/index.html").permitAll()

                    // Autenticación
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/auth/estudiante/login").permitAll()

                    // Validación pública del código infantil
                    .requestMatchers("/api/estudiantes/validar").permitAll()

                    // Los demás endpoints requieren JWT
                    .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception

                    // No existe una autenticación válida
                    .authenticationEntryPoint(
                            (request, response, authException) -> {

                        ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .error(
                                        HttpStatus.UNAUTHORIZED
                                                .getReasonPhrase())
                                .message(
                                        "Debes iniciar sesión para acceder a este recurso.")
                                .path(request.getRequestURI())
                                .build();

                        response.setStatus(
                                HttpStatus.UNAUTHORIZED.value());

                        response.setContentType(
                                MediaType.APPLICATION_JSON_VALUE);

                        response.setCharacterEncoding("UTF-8");

                        objectMapper.writeValue(
                                response.getOutputStream(),
                                error);
                    })

                    // Existe autenticación, pero el rol no tiene permiso
                    .accessDeniedHandler(
                            (request, response, accessDeniedException) -> {

                        ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .error(
                                        HttpStatus.FORBIDDEN
                                                .getReasonPhrase())
                                .message(
                                        "No tienes permisos para acceder a este recurso.")
                                .path(request.getRequestURI())
                                .build();

                        response.setStatus(
                                HttpStatus.FORBIDDEN.value());

                        response.setContentType(
                                MediaType.APPLICATION_JSON_VALUE);

                        response.setCharacterEncoding("UTF-8");

                        objectMapper.writeValue(
                                response.getOutputStream(),
                                error);
                    })
            )

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${cors.allowed-origins:http://localhost:4200,http://localhost:4201,http://localhost:4202,http://localhost:4203}")
            String allowedOrigins) {

        CorsConfiguration configuration =
                new CorsConfiguration();

        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toList();

        configuration.setAllowedOrigins(origins);

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                ));

        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type",
                        "Accept"
                ));

        configuration.setExposedHeaders(
                List.of("Authorization"));

        configuration.setAllowCredentials(false);

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}