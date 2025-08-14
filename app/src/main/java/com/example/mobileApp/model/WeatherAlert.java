package com.example.mobileApp.model;

import java.math.BigDecimal;
import java.util.Date;

public class WeatherAlert {

    private Integer id;
    private AlertType type;
    private String message;
    private BigDecimal detectedValue;
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getDetectedValue() {
        return detectedValue;
    }

    public void setDetectedValue(BigDecimal detectedValue) {
        this.detectedValue = detectedValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}


