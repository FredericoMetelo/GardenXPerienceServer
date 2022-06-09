package com.scmuWateringSystem.wateringSystem.application.controllers;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Models.WaterConfig;
import com.scmuWateringSystem.wateringSystem.application.services.ConfigsService;
import com.scmuWateringSystem.wateringSystem.application.Repository.WaterConfigsJpaRepository;
import com.scmuWateringSystem.wateringSystem.application.services.MetricsService;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;
//import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import com.scmuWateringSystem.wateringSystem.mqtt.MqttConfigs;
import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import com.scmuWateringSystem.wateringSystem.mqtt.Topics;
import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final WaterConfigsJpaRepository waterConfigsRepo;

    @PostMapping("/testPostW")
    public String testPostWaterConfigs(@RequestBody WaterConfig waterConfig){
        waterConfig.setId("ID1 " +System.currentTimeMillis());
        WaterConfig w = waterConfigsRepo.save(waterConfig);
        //mqtGateway.sendToMqtt("HELLO FROM HEAVEN", MqttConfigs.MANUAL_WATERING_NOTIFICATION_TOPIC);
        return w.getId();
    }

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
    public ConfigsBody config(@PathVariable String id, @PathVariable String automatism, @RequestBody ConfigsBody cb){
        try{
            switch (cb.getAutomatismo()){
                case "rega" -> {
                    String humidityThreshold = cb.getThresholds().get(0);
                    String temperatureThreshold = cb.getThresholds().get(1);
                    mqtGateway.sendToMqtt(humidityThreshold,topics.getHumidityMin());
                    mqtGateway.sendToMqtt(temperatureThreshold,topics.getTemperatureMin());
                    String timeTofun =  (cb.getTimeToFucntion().isEmpty()) ?  "45" : cb.getTimeToFucntion();
                    configsService.UpdateWaterConfig(Float.parseFloat(humidityThreshold), Float.parseFloat(temperatureThreshold), Integer.parseInt(timeTofun));
                    logger.info("config of: "+ humidityThreshold + " " + temperatureThreshold);
                    return configsService.getConfigsBody("rega");
                }
                case "luz" -> {
                    String lightThreshold = cb.getThresholds().get(0);
                    mqtGateway.sendToMqtt(lightThreshold,topics.getLuminosityData());
                    configsService.UpdateLightConfig(Float.parseFloat(lightThreshold),  Integer.parseInt(cb.getTimeToFucntion()));
                    return configsService.getConfigsBody("luz");
                }
            }
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "This was not supposed to happen");
        }
        return cb;
    }


    // POST http://localhost:8080/api/start/0/rega
    // POST http://localhost:8080/api/start/0/luz
    @PostMapping("/start/{id}/{automatism}")
    public String startActuator(@PathVariable String id, @PathVariable String automatism){
        return "start";
    }

    // POST http://localhost:8080/api/stop/0/rega
    // POST http://localhost:8080/api/stop/0/luz
    @PostMapping("/stop/{id}/{automatism}")
    public String stopActuator(@PathVariable String id, @PathVariable String automatism){
        return "stop";
    }

}