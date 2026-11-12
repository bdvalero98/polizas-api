package com.seguros.polizas.config;

import com.seguros.polizas.entity.Poliza;
import com.seguros.polizas.enums.TipoPoliza;
import com.seguros.polizas.repository.PolizaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final PolizaRepository polizaRepository;

    public DataLoader(PolizaRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    @Override
    public void run(String... args) {
        Poliza individual = new Poliza(
                TipoPoliza.INDIVIDUAL, 12, LocalDate.now(), new BigDecimal("1500000")
        );
        individual.agregarRiesgoInicial("Cra 7 # 45-10, Bogota", "Jose Perez");

        Poliza colectiva = new Poliza(
                TipoPoliza.COLECTIVA, 12, LocalDate.now(), new BigDecimal("2000000")
        );
        colectiva.agregarRiesgoInicial("Calle 100 # 15-20 Apto 301", "Maria Gomez");
        colectiva.agregarRiesgoInicial("Calle 100 # 15-20 Apto 502", "Carlos Ruiz");

        polizaRepository.save(individual);
        polizaRepository.save(colectiva);
    }
}
