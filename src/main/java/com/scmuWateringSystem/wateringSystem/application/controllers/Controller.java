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

import static com.scmuWateringSystem.wateringSystem.application.services.ConfigsService.LIGHT_AUTOMATISM;
import static com.scmuWateringSystem.wateringSystem.application.services.ConfigsService.WATERING_AUTOMATISM;

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
        return "TODO";
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
    public void config(@PathVariable String id, @PathVariable String automatism ,@RequestBody ConfigsBody cb){
        switch (cb.getAutomatismo()){
            case WATERING_AUTOMATISM -> {
                configsService.publishWateringConfigs(cb);
            }
            case LIGHT_AUTOMATISM -> {
                configsService.publishLightConfigs(cb);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unexpected value: " + cb.getAutomatismo());
        }
    }
}