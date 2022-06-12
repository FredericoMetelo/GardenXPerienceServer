package com.scmuWateringSystem.wateringSystem.application.services;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.MetricsJPARepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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

import static com.scmuWateringSystem.wateringSystem.mqtt.ReceivedMessagesServiceHandler.*;

@Service
@AllArgsConstructor
public class MetricsService {

    private final MetricsJPARepository metricsJPARepository;

    public List<Metric> getRelevantLight(){
        Page<Metric> page = metricsJPARepository.findAllByMagnitudeType(LUMINOSITY_MAGNITUDE,pagination());
        return page.toList();
    }

    public List<Metric> getRelevantHumidity(){
        Page<Metric> page = metricsJPARepository.findAllByMagnitudeType(HUMIDITY_MAGNITUDE,pagination());
        return page.toList();
    }

    public List<Metric> getRelevantTemperature(){
        Page<Metric> page = metricsJPARepository.findAllByMagnitudeType(TEMPERATURE_MAGNITUDE,pagination());
        return page.toList();
    }
    public void addMetric(Metric metric){
        metricsJPARepository.save(metric);
    }
    private String randomId(){
        return UUID.randomUUID().toString();
    }
    private Pageable pagination(){
        return PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
    }
}
