package com.scmuWateringSystem.wateringSystem.application.arguments;

import org.springframework.data.util.Pair;

import java.util.List;

public class ConfigsBody {
    private String automatismo;
    private String id;
    private List<String> thresholds;
    private String timeToFucntion;
}
