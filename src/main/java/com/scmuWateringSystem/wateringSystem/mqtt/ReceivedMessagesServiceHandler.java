package com.scmuWateringSystem.wateringSystem.mqtt;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import com.scmuWateringSystem.wateringSystem.application.Repository.MetricsJPARepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ReceivedMessagesServiceHandler {

    private final MqttGateway mqttGateway;
    private final Topics topics;
    private final MetricsJPARepository metricsJPARepository;
    public static final String LUMINOSITY_MAGNITUDE = "luminosity";
    public static final String HUMIDITY_MAGNITUDE = "humidity";
    public static final String TEMPERATURE_MAGNITUDE = "temperature";


    private String currentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
    public Metric makeMetric(String value, String magnitudeType){
        Metric metric = new Metric();
        metric.setDate(currentDate());
        metric.setId(UUID.randomUUID().toString());
        metric.setValue(value);
        metric.setMagnitudeType(magnitudeType);
        return metric;
    }
    public void handleAllTopics(String topic,String message){
        log.info("LOG FROM TOPIC {}",topic);
        if(topic.equals(topics.getWateringEvent())){
            handleWateringPending(message);
        }else if(topic.equals(topics.getLuminosityData())){
            Metric metric = makeMetric(message,LUMINOSITY_MAGNITUDE);
            metricsJPARepository.save(metric);
            log.info("UPDATED LUMINOSITY DATA: {}",message);
        }else if(topic.equals(topics.getHumidityData())){
            Metric metric = makeMetric(message,HUMIDITY_MAGNITUDE);
            metricsJPARepository.save(metric);
            log.info("UPDATED HUMIDITY DATA: {}",message);
        }else if(topic.equals(topics.getTemperatureData())){
            Metric metric = makeMetric(message,TEMPERATURE_MAGNITUDE);
            metricsJPARepository.save(metric);
            log.info("UPDATED TEMPERATURE DATA: {}",message);
        }
    }
    private void handleWateringPending(String message){
        //TODO process message, publish it
        log.info("GOING TO READ MESSAGE FROM TOPIC {}",topics.getPendingWatering());
        mqttGateway.sendToMqtt(message,topics.getPendingWatering());
        log.info("READ {} BYTES FROM {} TOPIC",message.length(),topics.getPendingWatering());
    }
}
