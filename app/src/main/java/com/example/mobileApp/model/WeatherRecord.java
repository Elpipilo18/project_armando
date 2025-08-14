package com.example.mobileApp.model;

import java.math.BigDecimal;

public class WeatherRecord {

    private Integer id;
    private LectureType type;
    private String value;
    private String dateLabel;
    private String time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LectureType getType() {
        return type;
    }

    public void setType(LectureType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
