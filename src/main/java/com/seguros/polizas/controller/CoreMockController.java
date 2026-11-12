package com.seguros.polizas.controller;

import com.seguros.polizas.dto.request.CoreEventoRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    private static final Logger log = LoggerFactory.getLogger(CoreMockController.class);

    @PostMapping("/evento")
    public Map<String, String> recibirEvento(@Valid @RequestBody CoreEventoRequest request) {
        log.info("[CORE-MOCK] Operacion enviada al CORE -> evento={}, polizaId={}",
                request.evento(), request.polizaId());

        return Map.of("status", "RECIBIDO");
    }
}
