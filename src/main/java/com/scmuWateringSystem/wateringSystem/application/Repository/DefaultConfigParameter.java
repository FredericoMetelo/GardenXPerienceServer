package com.scmuWateringSystem.wateringSystem.application.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "DEFAULTCONFIGPARAMETERS")
@AllArgsConstructor
public class DefaultConfigParameter {

    /**Parameters:
     * parameters will be:
     * WateringHumidityTrigger
     * WateringTemperatureTrigger
     * WateringDuration
     * LightLuminosityTrigger
     * LightDuration
     */
    @Id
    private String parameterName;
    private int parameterValue;

    public DefaultConfigParameter() {}
}
