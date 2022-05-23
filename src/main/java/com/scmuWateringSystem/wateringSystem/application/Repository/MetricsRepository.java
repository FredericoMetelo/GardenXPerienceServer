package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;

import java.util.ArrayList;
import java.util.List;

public class MetricsRepository {
    List<Metric> lightData;
    List<Metric> humidityData;


    // Code Database Accesses!
    public MetricsRepository() {
        this.lightData = new ArrayList<>();
        this.humidityData = new ArrayList<>();
    }

    // TODO Go to the fucking Database to fetch this shit. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Metric> getRelevantLight(){
        return lightData.subList(Math.max(0, lightData.size() - 20), lightData.size());
    }

    // TODO Go to the fucking Database to fetch this shit. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Metric> getRelevantHumidity(){
        return humidityData.subList(Math.max(0, humidityData.size() - 20), humidityData.size());
    }

}
