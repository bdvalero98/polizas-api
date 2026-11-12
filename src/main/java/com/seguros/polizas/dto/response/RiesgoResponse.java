package com.seguros.polizas.dto.response;

import com.seguros.polizas.entity.Riesgo;
import com.seguros.polizas.enums.EstadoRiesgo;

public record RiesgoResponse(
        Long id,
        Long polizaId,
        String direccionInmueble,
        String arrendatario,
        EstadoRiesgo estado
) {
    public static RiesgoResponse from(Riesgo r) {
        return new RiesgoResponse(
                r.getId(), r.getPoliza().getId(), r.getDireccionInmueble(), r.getArrendatario(), r.getEstado()
        );
    }
}
