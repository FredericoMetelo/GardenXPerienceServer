package com.scmuWateringSystem.wateringSystem.application.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "waterConfigs")
public class WaterConfig {

    private float humidityThreshold;
    private float temperatureThreshold;
    private int timeToFunction;
    @Id
    private String id;
    public WaterConfig (float humidityThreshold, float temperatureThreshold, int timeToFunction){
        this.humidityThreshold = humidityThreshold;
        this.temperatureThreshold = temperatureThreshold;
        this.timeToFunction = timeToFunction;
    }

    public WaterConfig() {

    }
}
