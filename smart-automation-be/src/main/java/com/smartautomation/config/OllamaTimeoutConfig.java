package com.smartautomation.config;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import jakarta.annotation.PostConstruct;

@Configuration
public class OllamaTimeoutConfig {
    
    @PostConstruct
    public void setJvmTimeout() {
        // Set the Ollama timeout to 60 seconds
        System.setProperty("ollama.timeout", "60000");
    }

    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return builder -> {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(30000); // 60 seconds
            requestFactory.setReadTimeout(0); // 60 seconds
            builder.requestFactory(requestFactory);
        };
    }
}
