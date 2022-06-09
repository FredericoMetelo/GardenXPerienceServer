package com.scmuWateringSystem.wateringSystem.application.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "lightConfigs")
@AllArgsConstructor
public class LightConfig {
    private float luminosityThreshold;
    private int timeToFunction;
    @Id
    private String id;
    public LightConfig(){}
}
