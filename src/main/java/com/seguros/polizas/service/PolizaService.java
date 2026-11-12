package com.seguros.polizas.service;

import com.seguros.polizas.dto.response.PolizaResponse;
import com.seguros.polizas.dto.response.RiesgoResponse;
import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;
import com.seguros.polizas.exception.NotFoundException;
import com.seguros.polizas.repository.PolizaRepository;
import com.seguros.polizas.repository.RiesgoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;

    public PolizaService(PolizaRepository polizaRepository, RiesgoRepository riesgoRepository) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
    }

    @Transactional(readOnly = true)
    public List<PolizaResponse> listar(TipoPoliza tipo, EstadoPoliza estado) {
        return polizaRepository.buscarPolizas(tipo, estado).stream()
                .map(PolizaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RiesgoResponse> listarRiesgos(Long polizaId) {

        if (!polizaRepository.existsById(polizaId)) {
            throw new NotFoundException("Poliza " + polizaId + " no encontrada");
        }

        return riesgoRepository.findByPolizaId(polizaId).stream()
                .map(RiesgoResponse::from)
                .toList();
    }
}
