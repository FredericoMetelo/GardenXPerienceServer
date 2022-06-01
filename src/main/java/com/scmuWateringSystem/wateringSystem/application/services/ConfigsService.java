package com.scmuWateringSystem.wateringSystem.application.services;

import com.scmuWateringSystem.wateringSystem.application.Models.LightConfig;
import com.scmuWateringSystem.wateringSystem.application.Models.WaterConfig;
import com.scmuWateringSystem.wateringSystem.application.Repository.LightConfigsJpaRepository;
import com.scmuWateringSystem.wateringSystem.application.Repository.WaterConfigsJpaRepository;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfigsService {
    private static final String WATER_CONFIG_ID = "WATER_CONFIG_ID";
    private static final String LIGHT_CONFIG_ID = "LIGHT_CONFIG_ID";
    private static final String WATERING_AUTOMATISM = "rega";
    private static final String LIGHT_AUTOMATISM = "ligh";

    private final LightConfigsJpaRepository lightConfigsJpaRepository;
    private final WaterConfigsJpaRepository waterConfigsJpaRepository;
    private String randomId(){
        return UUID.randomUUID().toString();
    }
    public void UpdateWaterConfig(float humidityThreshold, float temperatureThreshold, int timeToFunction){
        WaterConfig waterConfig = new WaterConfig(humidityThreshold,temperatureThreshold,timeToFunction, WATER_CONFIG_ID);
        waterConfigsJpaRepository.save(waterConfig);
    }

    public void UpdateLightConfig(float luminosityThreshold, int timeToFunction){
        LightConfig lightConfig = new LightConfig(luminosityThreshold,timeToFunction,LIGHT_CONFIG_ID);
        lightConfigsJpaRepository.save(lightConfig);
    }

    private ConfigsBody getWaterConfigBody (){
        List<String> a = new ArrayList<>();
        WaterConfig waterConfig = waterConfigsJpaRepository.getById(WATER_CONFIG_ID);
        a.add(""+waterConfig.getHumidityThreshold());
        a.add(""+waterConfig.getTemperatureThreshold());
        return new ConfigsBody("rega", "0", a, ""+waterConfig.getTimeToFunction());
    }

    private ConfigsBody getLightConfigBody(){
        List<String> a = new ArrayList<>();
        LightConfig lightConfig = lightConfigsJpaRepository.getById(LIGHT_CONFIG_ID);
        a.add(""+lightConfig.getLuminosityThreshold());
        return new ConfigsBody("luz", "0", a, ""+lightConfig.getTimeToFunction());
    }

    public ConfigsBody getConfigsBody(String automatism){
        switch (automatism) {
            case WATERING_AUTOMATISM : return getWaterConfigBody();
            case LIGHT_AUTOMATISM : return getLightConfigBody();
            default : return null;
        }
    }
}
