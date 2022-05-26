package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.LightConfig;
import com.scmuWateringSystem.wateringSystem.application.Models.WaterConfig;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;

import java.util.ArrayList;
import java.util.List;

public class ConfigsRepository {
    WaterConfig waterConfig;
    LightConfig lightConfig;

    // TODO Ir à BD buscar isto sff??
    public ConfigsRepository() {
        this.waterConfig = new WaterConfig(0.5f, 20, 10);
        this.lightConfig = new LightConfig(0.5f, 10 );
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

    public ConfigsBody getWaterConfigBody (){
        List<String> a = new ArrayList<>();
        a.add(""+waterConfig.getHumidityThreshold());
        a.add(""+waterConfig.getTemperatureThreshold());
        return new ConfigsBody("rega", "0", a, ""+waterConfig.getTimeToFunction());
    }

    public ConfigsBody getLightConfigBody(){
        List<String> a = new ArrayList<>();
        a.add(""+lightConfig.getLuminosityThreshold());
        return new ConfigsBody("luz", "0", a, ""+lightConfig.getTimeToFunction());
    }
}
