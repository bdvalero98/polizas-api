package com.seguros.polizas.entity;


import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "polizas")
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPoliza tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPoliza estado = EstadoPoliza.ACTIVA;

    @Column(nullable = false)
    private int vigenciaMeses;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal canonMensual;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal prima;

    protected Poliza() {
    }

    public Poliza(Long id, int vigenciaMeses, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal canonMensual, BigDecimal prima) {
        this.id = id;
        this.vigenciaMeses = vigenciaMeses;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin.plusMonths(vigenciaMeses);
        this.canonMensual = canonMensual.setScale(2, RoundingMode.HALF_UP);
        this.prima = calcularPrima(this.canonMensual, vigenciaMeses);
    }

    private static BigDecimal calcularPrima(BigDecimal canon, int meses) {
        return canon.multiply(BigDecimal.valueOf(meses)).setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public TipoPoliza getTipo() {
        return tipo;
    }

    public EstadoPoliza getEstado() {
        return estado;
    }

    public int getVigenciaMeses() {
        return vigenciaMeses;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public BigDecimal getCanonMensual() {
        return canonMensual;
    }

    public BigDecimal getPrima() {
        return prima;
    }
}
