package com.seguros.polizas.service;

import com.seguros.polizas.dto.request.CoreEventoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CoreMockGateway implements CoreGateway {

    private static final Logger log = LoggerFactory.getLogger(CoreMockGateway.class);

    private final RestClient restClient;

    public CoreMockGateway(@Value("${core.base-url}") String baseUrl,
                           @Value("${core.api-key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-api-key", apiKey)
                .build();
    }

    @Override
    public void notificarEvento(String evento, Long polizaId) {
        try {
            restClient.post()
                    .uri("/core-mock/evento")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new CoreEventoRequest(evento, polizaId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("[CORE-MOCK] Error al enviar evento al CORE -> evento={}, polizaId={}", evento, polizaId, e);
        }
    }
}
