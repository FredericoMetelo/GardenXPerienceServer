package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class MetricsRepository {
    List<Metric> lightData;
    List<Metric> humidityData;

    List<Metric> temperatureData;
    // Code Database Accesses!

    public MetricsRepository() {
        this.lightData = new ArrayList<>();
        this.humidityData = new ArrayList<>();
        this.temperatureData = new ArrayList<>();

        for(int i = 0; i <= 20; i++){
            lightData.add(new Metric(String.format("%d", i), ""+ ((50)/(i+1))));
        }
        for(int i = 0; i <= 20; i++){
            humidityData.add(new Metric(String.format("%d", i), "" + (i/20*100)));
        }
        for(int i = 0; i <= 20; i++){
            temperatureData.add(new Metric(String.format("%d", i), "" + (i/20*100)));
        }
    }

    // TODO Go to the fucking Database to fetch this shit. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Metric> getRelevantLight(){
        return lightData.subList(Math.max(0, lightData.size() - 20), lightData.size());
    }

    // TODO Go to the fucking Database to fetch this shit. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Metric> getRelevantHumidity(){
        return humidityData.subList(Math.max(0, humidityData.size() - 20), humidityData.size());
    }

    // TODO Go to the fucking Database to fetch this shit. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Metric> getRelevantTemperature(){
        return temperatureData.subList(Math.max(0, temperatureData.size() - 20), temperatureData.size());
    }
}
