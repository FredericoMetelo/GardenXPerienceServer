package com.scmuWateringSystem.wateringSystem.application.services;

import com.scmuWateringSystem.wateringSystem.application.Repository.*;
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

    private static final String WATERING_AUTOMATISM = "rega";
    private static final String LIGHT_AUTOMATISM = "ligh";

    private final DefaultConfigParameterRepository defaultConfigParameterRepository;

    private String randomId(){
        return UUID.randomUUID().toString();
    }

    public void updateParameter(String parameterName, int newValue){
        DefaultConfigParameter dcp = new DefaultConfigParameter(parameterName,newValue);
        defaultConfigParameterRepository.save(dcp);
    }

    private ConfigsBody getWaterConfigBody (){
        List<String> a = new ArrayList<>();
        int humidityThreshold = defaultConfigParameterRepository.getById(ParameterNames.WateringHumidityTrigger.toString()).getParameterValue();
        int temperatureThreshold = defaultConfigParameterRepository.getById(ParameterNames.WateringTemperatureTrigger.toString()).getParameterValue();
        int wateringDuration = defaultConfigParameterRepository.getById(ParameterNames.WateringDuration.toString()).getParameterValue();

        a.add(""+humidityThreshold);
        a.add(""+temperatureThreshold);
        return new ConfigsBody("rega", "0", a, ""+wateringDuration);
    }

    private ConfigsBody getLightConfigBody(){
        List<String> a = new ArrayList<>();
        int luminosityThreshold = defaultConfigParameterRepository.getById(ParameterNames.LightLuminosityTrigger.name()).getParameterValue();
        int luminosityDuration = defaultConfigParameterRepository.getById(ParameterNames.LightDuration.name()).getParameterValue();
        a.add(""+luminosityThreshold);
        return new ConfigsBody("luz", "0", a, ""+luminosityDuration);
    }

    public ConfigsBody getConfigsBody(String automatism){
        switch (automatism) {
            case WATERING_AUTOMATISM : return getWaterConfigBody();
            case LIGHT_AUTOMATISM : return getLightConfigBody();
            default : return null;
        }
    }
}
