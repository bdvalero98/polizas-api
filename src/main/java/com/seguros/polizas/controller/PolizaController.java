package com.seguros.polizas.controller;

import com.seguros.polizas.dto.response.PolizaResponse;
import com.seguros.polizas.dto.response.RiesgoResponse;
import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;
import com.seguros.polizas.service.PolizaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService polizaService;

    public PolizaController(PolizaService polizaService) {
        this.polizaService = polizaService;
    }

    @GetMapping
    public List<PolizaResponse> listar(@RequestParam(required = false) TipoPoliza tipo,
                                       @RequestParam(required = false) EstadoPoliza estado) {
        return polizaService.listar(tipo, estado);
    }

    @GetMapping("/{id}/riesgos")
    public List<RiesgoResponse> listarRiesgos(@PathVariable Long id) {
        return polizaService.listarRiesgos(id);
    }
}
