package com.seguros.polizas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoreEventoRequest(
        @NotBlank(message = "El evento es obligatorio")
        String evento,

        @NotNull(message = "El id es obligatorio")
        Long polizaId
) {
}
