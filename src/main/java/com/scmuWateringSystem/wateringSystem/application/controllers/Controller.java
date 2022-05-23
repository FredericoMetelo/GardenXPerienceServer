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

    @GetMapping()
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
    }

}
