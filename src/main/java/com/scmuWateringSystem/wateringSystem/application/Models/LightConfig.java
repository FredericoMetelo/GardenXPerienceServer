package com.scmuWateringSystem.wateringSystem.application.Models;

public class LightConfig {
    private float luminosityThreshold;
    private int timeToFunction;

    public LightConfig(float luminosityThreshold, int timeToFunction) {
        this.luminosityThreshold = luminosityThreshold;
        this.timeToFunction = timeToFunction;
    }

    public float getLuminosityThreshold() {
        return luminosityThreshold;
    }

    public void setLuminosityThreshold(float humidityThreshold) {
        this.luminosityThreshold = humidityThreshold;
    }


    public int getTimeToFunction() {
        return timeToFunction;
    }

    public void setTimeToFunction(int timeToFunction) {
        this.timeToFunction = timeToFunction;
    }
}
