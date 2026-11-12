package com.seguros.polizas.service;

import com.seguros.polizas.dto.request.RiesgoRequest;
import com.seguros.polizas.dto.response.PolizaResponse;
import com.seguros.polizas.dto.response.RiesgoResponse;
import com.seguros.polizas.entity.Poliza;
import com.seguros.polizas.entity.Riesgo;
import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;
import com.seguros.polizas.exception.BusinessException;
import com.seguros.polizas.exception.NotFoundException;
import com.seguros.polizas.repository.PolizaRepository;
import com.seguros.polizas.repository.RiesgoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;
    private final BigDecimal ipcPorDefecto;

    public PolizaService(PolizaRepository polizaRepository, RiesgoRepository riesgoRepository,
                         @Value("${negocio.ipc-por-defecto}") BigDecimal ipcPorDefecto) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
        this.ipcPorDefecto = ipcPorDefecto;
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

    @Transactional
    public PolizaResponse renovarPoliza(Long polizaId, BigDecimal ipc) {
        Poliza poliza = obtenerPoliza(polizaId);
        poliza.renovar(ipc != null ? ipc : ipcPorDefecto);
        polizaRepository.save(poliza);

        return PolizaResponse.from(poliza);
    }

    @Transactional
    public PolizaResponse cancelarPoliza(Long polizaId) {
        Poliza poliza = obtenerPoliza(polizaId);
        poliza.cancelar();
        polizaRepository.save(poliza);

        return PolizaResponse.from(poliza);
    }

    @Transactional
    public RiesgoResponse agregarRiesgo(Long polizaId, RiesgoRequest request) {
        Poliza poliza = obtenerPoliza(polizaId);
        Riesgo riesgo = poliza.agregarRiesgo(request.direccionInmueble(), request.arrendatario());

        polizaRepository.saveAndFlush(poliza);

        return RiesgoResponse.from(riesgo);
    }

    @Transactional
    public RiesgoResponse cancelarRiesgo(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(
                        () -> new NotFoundException("Riesgo " + riesgoId + " no encontrado")
                );

        if (riesgo.estaCancelado()) {
            throw new BusinessException("El riesgo ya esta cancelado");
        }

        riesgo.cancelar();
        riesgoRepository.save(riesgo);

        return RiesgoResponse.from(riesgo);
    }

    private Poliza obtenerPoliza(Long id) {
        return polizaRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Poliza " + id + " no encontrada")
                );
    }
}
