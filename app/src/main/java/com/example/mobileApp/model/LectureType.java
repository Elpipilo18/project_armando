package com.example.mobileApp.model;

public enum LectureType {
    TEMPERATURE(1, "Temperatura"),
    HUMIDITY(2, "Humedad"),
    PRESSURE(3, "Presión");

    private final int id;
    private final String description;

    LectureType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static LectureType get(int id) {
        return values()[id - 1];
    }
}
