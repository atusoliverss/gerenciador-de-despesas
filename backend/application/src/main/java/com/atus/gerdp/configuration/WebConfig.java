package com.atus.gerdp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurações da parte Web da aplicação.
 * Principalmente, libera o acesso do frontend (React) ao backend (Spring).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Define as regras de CORS (Cross-Origin Resource Sharing).
     * Isso permite que o frontend em http://localhost:5173
     * possa fazer chamadas para a nossa API no backend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera todos os endpoints da API
                .allowedOrigins("http://localhost:5173") // Permite acesso vindo do frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Libera os métodos HTTP necessários
                .allowedHeaders("*"); // Libera todos os cabeçalhos
    }
}
