package com.seguros.polizas.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record RenovarRequest(
        @DecimalMin(value = "0.0", message = "El IPC debe ser un valor positivo")
        @DecimalMax(value = "1.0", message = "El IPC se expresa como fraccion (0.05 = 5%)")
        BigDecimal ipc
) {
}
