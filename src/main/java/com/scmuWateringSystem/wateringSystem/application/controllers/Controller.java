package com.scmuWateringSystem.wateringSystem.application.controllers;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.ParameterNames;
import com.scmuWateringSystem.wateringSystem.application.services.ConfigsService;
import com.scmuWateringSystem.wateringSystem.application.services.MetricsService;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;
import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import com.scmuWateringSystem.wateringSystem.mqtt.Topics;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api")
@AllArgsConstructor
//@Slf4j
@RestController
public class Controller {
    private final MqttGateway mqtGateway;
    private MetricsService metricsService;
    private final ConfigsService configsService;
    private final Topics topics;

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping
    public String test(){
        return "OLA";
    }

    // GET http://localhost:8080/api/waterTempData/0
    @GetMapping("/waterTempData/{id}")
    public List<Metric> waterTempData(@PathVariable String id){
        return metricsService.getRelevantTemperature();
    }

    // GET http://localhost:8080/api/waterHumData/0
    @GetMapping("/waterHumData/{id}")
    public List<Metric> waterHumData(@PathVariable String id){
        return metricsService.getRelevantHumidity();
    }

    // GET http://localhost:8080/api/lightData/0
    @GetMapping("/lightData/{id}")
    public List<Metric> lightData(@PathVariable String id){
        return metricsService.getRelevantLight();
    }


    // GET http://localhost:8080/api/getActuatorStatus/0
    @GetMapping("/getActuatorStatus/{id}")
    public String getActuatorStatus(@PathVariable String id){
        // Device ID is to be ignored.
        // TODO Perguntar Sink Node estado e reencaminhar?
        return "Loles, TODO";
    }

    // GET http://localhost:8080/api/getConfig/0/rega
    // GET http://localhost:8080/api/getConfig/0/luz
    @GetMapping("/getConfig/{id}/{automatism}")
    public ConfigsBody getConfig(@PathVariable String id, @PathVariable String automatism){
        // Device ID is to be ignored.
        ConfigsBody cb = configsService.getConfigsBody(automatism);
        if(cb==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "body not found");
        }
        return cb;
    }

    // POST http://localhost:8080/api/config/0/rega
    // POST http://localhost:8080/api/config/0/luz
    @PostMapping("/config/{id}/{automatism}")
    public void config(@PathVariable String id, @PathVariable String automatism, @RequestBody ConfigsBody cb){
        try{
            switch (cb.getAutomatismo()){
                case "rega" -> {
                    String humidityThreshold = cb.getThresholds().get(0);
                    String temperatureThreshold = cb.getThresholds().get(1);
                    int duration =  Integer.parseInt((cb.getTimeToFucntion().isEmpty()) ?  "45" : cb.getTimeToFucntion());

                    mqtGateway.sendToMqtt(humidityThreshold,topics.getHumidityMin());
                    mqtGateway.sendToMqtt(temperatureThreshold,topics.getTemperatureMin());
                    configsService.updateParameter(ParameterNames.WateringHumidityTrigger.name(),Integer.parseInt(humidityThreshold));
                    configsService.updateParameter(ParameterNames.WateringTemperatureTrigger.name(),Integer.parseInt(temperatureThreshold));
                    configsService.updateParameter(ParameterNames.WateringDuration.name(),duration);
                    logger.info("config of: "+ humidityThreshold + " " + temperatureThreshold);
                }
                case "luz" -> {
                    int lightThreshold = Integer.parseInt(cb.getThresholds().get(0));
                    int lightDuration =  Integer.parseInt(cb.getTimeToFucntion());

                    mqtGateway.sendToMqtt(String.format("%|%"),topics.getLuminosityData());
                    configsService.updateParameter(ParameterNames.LightLuminosityTrigger.name(),lightThreshold);
                    configsService.updateParameter(ParameterNames.WateringDuration.name(),lightDuration);
                }
            }
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "This was not supposed to happen");
        }
    }
}