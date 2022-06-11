package com.scmuWateringSystem.wateringSystem.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt.topics")
@Data
public class Topics {
    private String humidityData;
    private String humidityMin;

    private String temperatureData;
    private String temperatureMin;

    private String pendingWatering;
    private String wateringEvent;
    private String luminosityData;
    private String lightEvent;
}
