package com.scmuWateringSystem.wateringSystem.application.Models;

public class WaterConfig {

    private float humidityThreshold;
    private float temperatureThreshold;
    private int timeToFunction;

    public WaterConfig(float humidityThreshold, float temperatureThreshold, int timeToFunction) {
        this.humidityThreshold = humidityThreshold;
        this.temperatureThreshold = temperatureThreshold;
        this.timeToFunction = timeToFunction;
    }

    public float getHumidityThreshold() {
        return humidityThreshold;
    }

    public void setHumidityThreshold(float humidityThreshold) {
        this.humidityThreshold = humidityThreshold;
    }

    public float getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(float temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
    }

    public int getTimeToFunction() {
        return timeToFunction;
    }

    public void setTimeToFunction(int timeToFunction) {
        this.timeToFunction = timeToFunction;
    }
}
