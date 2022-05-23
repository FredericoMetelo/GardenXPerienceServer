package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.LightConfig;
import com.scmuWateringSystem.wateringSystem.application.Models.WaterConfig;

public class ConfigsRepository {
    WaterConfig waterConfig;
    LightConfig lightConfig;

    // TODO Ir à BD buscar isto sff??
    public ConfigsRepository(WaterConfig waterConfig, LightConfig lightConfig) {
        this.waterConfig = waterConfig;
        this.lightConfig = lightConfig;
    }

    public void UpdateWaterConfig(float humidityThreshold, float temperatureThreshold, int timeToFunction){
        waterConfig.setHumidityThreshold(humidityThreshold);
        waterConfig.setTemperatureThreshold(temperatureThreshold);
        waterConfig.setTimeToFunction(timeToFunction);

        // TODO Write to the fucking Database.
    }

    public void UpdateLightConfig(float luminosityThreshold, int timeToFunction){
        lightConfig.setLuminosityThreshold(luminosityThreshold);
        lightConfig.setTimeToFunction(timeToFunction);

        // TODO Write to the fucking Database.
    }
}
