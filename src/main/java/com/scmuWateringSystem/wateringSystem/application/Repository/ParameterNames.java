package com.scmuWateringSystem.wateringSystem.application.Repository;

public enum ParameterNames {
    WateringHumidityTrigger,WateringTemperatureTrigger,WateringDuration,
    LightLuminosityTrigger,LightDuration;

    @Override
    public String toString() {
        return name();
    }
}
