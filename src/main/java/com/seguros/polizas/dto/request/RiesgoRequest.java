package com.seguros.polizas.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RiesgoRequest(
        @NotBlank(message = "La direccion del inmueble es obligatoria")
        String direccionInmueble,

        @NotBlank(message = "El arrendatario es obligatorio")
        String arrendatario
) {
}
