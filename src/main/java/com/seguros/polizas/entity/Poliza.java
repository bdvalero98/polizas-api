package com.seguros.polizas.entity;


import com.seguros.polizas.enums.EstadoPoliza;
import com.seguros.polizas.enums.TipoPoliza;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL)
    private List<Riesgo> riesgos = new ArrayList<>();

    protected Poliza() {
    }

    public Poliza(TipoPoliza tipo, int vigenciaMeses, LocalDate fechaInicio, BigDecimal canonMensual) {

        this.tipo = tipo;
        this.vigenciaMeses = vigenciaMeses;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaInicio.plusMonths(vigenciaMeses);
        this.canonMensual = canonMensual.setScale(2, RoundingMode.HALF_UP);
        this.prima = calcularPrima(this.canonMensual, vigenciaMeses);
    }

    public Riesgo agregarRiesgoInicial(String direccionInmueble, String arrendatario) {
        Riesgo riesgo = new Riesgo(this, direccionInmueble, arrendatario);
        riesgos.add(riesgo);
        return riesgo;
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

    public List<Riesgo> getRiesgos() {
        return riesgos;
    }
}
