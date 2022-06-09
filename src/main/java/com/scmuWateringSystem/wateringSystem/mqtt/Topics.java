package com.scmuWateringSystem.wateringSystem.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt.topics")
@Data
public class Topics {
    /**
     *
     mqtt.topics.humidityData=humidity/data
     mqtt.topics.humidityMin=humidity/min

     mqtt.topics.temperatureData=temperature/data
     mqtt.topics.temperatureMin=temperature/min


     mqtt.topics.water=water/set
     mqtt.topics.luminosityData=luminosity/data
     mqtt.topics.light=light/set
     */
    private String humidityData;
    private String humidityMin;

    private String temperatureData;
    private String temperatureMin;

    private String pendingWatering;
    private String wateringEvent;
    private String luminosityData;
    private String lightEvent;
}
