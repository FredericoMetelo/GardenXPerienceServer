package com.scmuWateringSystem.wateringSystem.application.controllers;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.ConfigsRepository;
import com.scmuWateringSystem.wateringSystem.application.Repository.MetricsRepository;
import com.scmuWateringSystem.wateringSystem.application.arguments.ConfigsBody;
import com.scmuWateringSystem.wateringSystem.application.arguments.StopWatering;
import com.scmuWateringSystem.wateringSystem.application.arguments.UpdateDeviceParametersArgs;
import com.scmuWateringSystem.wateringSystem.application.arguments.WaterGardenParameters;
//import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api")
@AllArgsConstructor
//@Slf4j
@RestController
public class Controller {
    //private final MqttGateway mqtGateway;
    private MetricsRepository metricsRepository;
    private ConfigsRepository configsRepository;

    public Controller() {
        this.metricsRepository = new MetricsRepository();
        this.configsRepository = new ConfigsRepository();
    }


    @GetMapping
    public String test(){
        return "OLA";
    }

    // GET http://localhost:8080/api/waterTempData/0
    @GetMapping("/waterTempData/{id}")
    public List<Metric> waterTempData(@PathVariable String id){
        return metricsRepository.getRelevantTemperature();
    }

    // GET http://localhost:8080/api/waterHumData/0
    @GetMapping("/waterHumData/{id}")
    public List<Metric> waterHumData(@PathVariable String id){
        return metricsRepository.getRelevantHumidity();
    }

    // GET http://localhost:8080/api/lightData/0
    @GetMapping("/lightData/{id}")
    public List<Metric> lightData(@PathVariable String id){
        return metricsRepository.getRelevantLight();
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
        ConfigsBody cb;
        switch (automatism) {
            case "rega" -> cb = configsRepository.getWaterConfigBody();
            case "light" -> cb = configsRepository.getLightConfigBody();
            default -> {
                HttpStatus a = HttpStatus.NOT_FOUND;
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found");
            }
        }
        return cb;
    }

    // POST http://localhost:8080/api/config/0/rega
    // POST http://localhost:8080/api/config/0/luz
    @PostMapping("/config/{id}/{automatism}")
    public String configWater(@PathVariable String id, @PathVariable String automatism, @RequestBody ConfigsBody cb){

        try{
            switch (cb.getAutomatismo()){
                case "rega" -> {
                    String ht = cb.getThresholds().get(0);
                    String tt = cb.getThresholds().get(1);
                    configsRepository.UpdateWaterConfig(Float.parseFloat(ht), Float.parseFloat(tt), Integer.parseInt(cb.getTimeToFucntion()));
                }
                case "luz" -> {
                    String ht = cb.getThresholds().get(0);
                    configsRepository.UpdateLightConfig(Float.parseFloat(ht),  Integer.parseInt(cb.getTimeToFucntion()));
                }
            }
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "This was not supposed to happen");
        }
        return "OK";
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

// Legacy API:
/*@GetMapping()
    public String test(){
        return "OLA";
    }

    @GetMapping("/viewSoilMoisture")
    public String viewSoilMoisture(){
        //mqtGateway.sendToMqtt("what is the soil moisture","sensors/temperature");
        return "soilMoisture";
    }

    @PostMapping("/waterTheGarden")
    public String waterTheGarden(@RequestBody WaterGardenParameters parameters){
        //mqtGateway.sendToMqtt("Water the garden at 2:10","#");

        return "soilMoisture";
    }

    @GetMapping("/history")
    public String viewHistory(){
        return "history";
    }

    @PostMapping("/stopWatering")
    public String stopWatering(@RequestBody StopWatering stopWatering){
        return "stop";
    }

    @GetMapping("/{deviceId}")
    public String viewDeviceStatus(@PathVariable("deviceId") String devideId){
        return devideId;
    }

    @PostMapping("/changeParameters")
    public String updateDeviceParameters(@RequestBody UpdateDeviceParametersArgs updateDeviceParametersArgs){
        return "parametersChanged";
    }

    @PostMapping("/{status}")
    public String changeLightState(@PathVariable String status){
        return "STATUS CHANGED";
    }*/