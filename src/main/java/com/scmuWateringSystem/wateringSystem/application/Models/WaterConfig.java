package com.scmuWateringSystem.wateringSystem.application.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "waterConfigs")
@AllArgsConstructor
public class WaterConfig {

    private float humidityThreshold;
    private float temperatureThreshold;
    private int timeToFunction;
    @Id
    private String id;
    public WaterConfig() {}
}
