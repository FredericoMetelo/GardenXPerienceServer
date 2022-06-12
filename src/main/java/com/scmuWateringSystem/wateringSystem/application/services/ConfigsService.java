package com.scmuWateringSystem.wateringSystem.application.services;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.*;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;
import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import com.scmuWateringSystem.wateringSystem.mqtt.Topics;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ConfigsService {

    public static final String WATERING_AUTOMATISM = "rega";
    public static final String LIGHT_AUTOMATISM = "luz";


    private final DefaultConfigParameterRepository defaultConfigParameterRepository;
    private final MqttGateway mqtGateway;
    private final Topics topics;
    private final MetricsService metricsService;

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
        return new ConfigsBody(WATERING_AUTOMATISM, "0", a, ""+wateringDuration);
    }

    private ConfigsBody getLightConfigBody(){
        List<String> a = new ArrayList<>();
        int luminosityThreshold = defaultConfigParameterRepository.getById(ParameterNames.LightLuminosityTrigger.name()).getParameterValue();
        int luminosityDuration = defaultConfigParameterRepository.getById(ParameterNames.LightDuration.name()).getParameterValue();
        a.add(""+luminosityThreshold);
        return new ConfigsBody(LIGHT_AUTOMATISM, "0", a, ""+luminosityDuration);
    }

    public ConfigsBody getConfigsBody(String automatism){
        switch (automatism) {
            case WATERING_AUTOMATISM : return getWaterConfigBody();
            case LIGHT_AUTOMATISM : return getLightConfigBody();
            default : return null;
        }
    }

    public void publishWateringConfigs(ConfigsBody cb){
        String humidityThreshold = cb.getThresholds().get(0);
        String temperatureThreshold = cb.getThresholds().get(1);
        int duration =  Integer.parseInt((cb.getTimeToFucntion().isEmpty()) ?  "45" : cb.getTimeToFucntion());
        mqtGateway.sendToMqtt(humidityThreshold,topics.getHumidityMin());
        mqtGateway.sendToMqtt(temperatureThreshold,topics.getTemperatureMin());
        updateParameter(ParameterNames.WateringHumidityTrigger.name(),Integer.parseInt(humidityThreshold));
        updateParameter(ParameterNames.WateringTemperatureTrigger.name(),Integer.parseInt(temperatureThreshold));
        updateParameter(ParameterNames.WateringDuration.name(),duration);

        //String metricValue =String.format("WATERING PARAMETERS UPDATED: humidityThreshold - %s, temperatureThreshold - %s, duration - %s ",humidityThreshold,temperatureThreshold,duration);
        //Metric metric = makeMetric(metricValue);
        log.info("configs of watering: humidity - {}, temperature - {}, duration - {} ",humidityThreshold,temperatureThreshold,duration);
    }
    public void publishLightConfigs(ConfigsBody cb){
        int lightThreshold = Integer.parseInt(cb.getThresholds().get(0));
        int lightDuration =  Integer.parseInt(cb.getTimeToFucntion());

        mqtGateway.sendToMqtt(String.format("%s|%s",lightThreshold,lightDuration),topics.getLuminosityData());
        updateParameter(ParameterNames.LightLuminosityTrigger.name(),lightThreshold);
        updateParameter(ParameterNames.LightDuration.name(),lightDuration);
        //String metricValue = String.format("LIGHT PARAMETERS UPDATED: lightThreshold - %s, lightDuration - %s",lightThreshold,lightDuration);
        //Metric metric = makeMetric(metricValue);
        log.info("configs of light: lightThreshold - {}, lightDuration - {}",lightThreshold,lightDuration);
    }

}
