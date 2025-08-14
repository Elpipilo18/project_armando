package com.example.mobileApp.model;

public enum AlertType {
    TEMPERATURA_ALTA(1, "Temperatura alta"),
    TEMPERATURA_BAJA(2, "Temperatura baja"),
    HUMEDAD_ALTA(3, "Humedad alta"),
    HUMEDAD_BAJA(4, "Humedad baja");

    private final int id;
    private final String description;

    AlertType(int id, String description) {
        this.id = id;
        this.description = description;
    }


    static AlertType get(int id) {
        return values()[id - 1];
    }
}
