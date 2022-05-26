package com.scmuWateringSystem.wateringSystem.application.arguments;

import org.springframework.data.util.Pair;

import java.util.List;

public class ConfigsBody {
    private String automatismo;
    private String id;
    private List<String> thresholds;
    private String timeToFucntion;

    public ConfigsBody(String automatismo, String id, List<String> thresholds, String timeToFucntion) {
        this.automatismo = automatismo;
        this.id = id;
        this.thresholds = thresholds;
        this.timeToFucntion = timeToFucntion;
    }

    public String getAutomatismo() {
        return automatismo;
    }

    public String getId() {
        return id;
    }

    public List<String> getThresholds() {
        return thresholds;
    }

    public String getTimeToFucntion() {
        return timeToFucntion;
    }
}
