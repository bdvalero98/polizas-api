package com.seguros.polizas.dto.response;

import com.seguros.polizas.entity.Poliza;
import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PolizaResponse(
        Long id,
        TipoPoliza tipo,
        EstadoPoliza estado,
        int vigenciaMeses,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal canonMensual,
        BigDecimal prima
) {
    public static PolizaResponse from(Poliza p) {
        return new PolizaResponse(
                p.getId(), p.getTipo(), p.getEstado(), p.getVigenciaMeses(), p.getFechaInicio(),
                p.getFechaFin(), p.getCanonMensual(), p.getPrima()
        );
    }
}
