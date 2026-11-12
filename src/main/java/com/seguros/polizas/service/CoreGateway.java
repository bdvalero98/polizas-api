package com.seguros.polizas.service;

public interface CoreGateway {

    void notificarEvento(String evento, Long polizaId);
}
