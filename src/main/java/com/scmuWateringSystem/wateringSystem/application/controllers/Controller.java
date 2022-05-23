package com.scmuWateringSystem.wateringSystem.application.controllers;

import com.scmuWateringSystem.wateringSystem.application.arguments.StopWatering;
import com.scmuWateringSystem.wateringSystem.application.arguments.UpdateDeviceParametersArgs;
import com.scmuWateringSystem.wateringSystem.application.arguments.WaterGardenParameters;
//import com.scmuWateringSystem.wateringSystem.mqtt.MqttGateway;
import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@AllArgsConstructor
//@Slf4j
@RestController
public class Controller {
    //private final MqttGateway mqtGateway;
    @GetMapping
    public String test(){
        return "OLA";
    }

    // GET http://localhost:8080/api/waterData/0
    @GetMapping("/waterData/{id}")
    public String waterData(@PathVariable String id){
        return "waterData";
    }

    // GET http://localhost:8080/api/lightData/0
    @GetMapping("/lightData/{id}")
    public String lightData(@PathVariable String id){
        return "lightData";
    }


    // GET http://localhost:8080/api/getActuatorStatus/0
    @GetMapping("/getActuatorStatus/{id}")
    public String getActuatorStatus(@PathVariable String id){
        // Device ID is to be ignored.
        return "getActuatorStatus";
    }

    // GET http://localhost:8080/api/getConfig/0/rega
    // GET http://localhost:8080/api/getConfig/0/luz
    @GetMapping("/getConfig/{id}/{automatism}")
    public String getConfig(@PathVariable String id, @PathVariable String automatism){
        // Device ID is to be ignored.

        return "getConfig";
    }

    // POST http://localhost:8080/api/config/0/rega
    // POST http://localhost:8080/api/config/0/luz
    @PostMapping("/configWater/{id}/{automatism}")
    public String config(@PathVariable String id, @PathVariable String automatism){
        return "config";
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