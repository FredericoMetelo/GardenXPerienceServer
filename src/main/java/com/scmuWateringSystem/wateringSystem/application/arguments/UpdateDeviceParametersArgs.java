package com.scmuWateringSystem.wateringSystem.application.arguments;

import lombok.Data;

@Data
public class UpdateDeviceParametersArgs {

    private String threshold; //SensorReactingValue
    private String deviceId;
}
