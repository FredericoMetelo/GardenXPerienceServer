package com.scmuWateringSystem.wateringSystem.application.services;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.MetricsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MetricsService {
    List<Metric> lightData;
    List<Metric> humidityData;

    @Autowired
    private MetricsJPARepository metricsJPARepository;

    List<Metric> temperatureData;

    // Code Database Accesses!

    public MetricsService() {
        this.lightData = new ArrayList<>();
        this.humidityData = new ArrayList<>();
        this.temperatureData = new ArrayList<>();

        for(int i = 0; i <= 20; i++){
            lightData.add(new Metric(String.format("%d", i), ""+ ((50)/(i+1)),randomId()));
        }
        for(int i = 0; i <= 20; i++){
            humidityData.add(new Metric(String.format("%d", i), "" + (i/20*100),randomId()));
        }
        for(int i = 0; i <= 20; i++){
            temperatureData.add(new Metric(String.format("%d", i), "" + (i/20*100),randomId()));
        }
    }

    public List<Metric> getRelevantLight(){
        //List<Metric> toStore = lightData.subList(Math.max(0, lightData.size() - 20), lightData.size());
        //return metricsJPARepository.saveAll(toStore);
        Page<Metric> page = metricsJPARepository.findAll(pagination());
        return page.toList();
    }

    public List<Metric> getRelevantHumidity(){
        Page<Metric> page = metricsJPARepository.findAll(pagination());
        return page.toList();
        //return humidityData.subList(Math.max(0, humidityData.size() - 20), humidityData.size());
        //return metricsJPARepository.saveAll(toStore);

    }

    public List<Metric> getRelevantTemperature(){
        //List<Metric> toStore = temperatureData.subList(Math.max(0, temperatureData.size() - 20), temperatureData.size());
        //return metricsJPARepository.saveAll(toStore);
        Page<Metric> page = metricsJPARepository.findAll(pagination());
        return page.toList();
    }
    private String randomId(){
        return UUID.randomUUID().toString();
    }
    private Pageable pagination(){
        return PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
    }
}
