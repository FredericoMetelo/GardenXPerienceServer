package com.scmuWateringSystem.wateringSystem.mqtt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReceivedMessagesServiceHandler {

    private final MqttGateway mqttGateway;
    private final Topics topics;

    public void handleAllTopics(String topic,String message){
        if(topic.equals(topics.getWateringEvent())){
            handleWateringPending(message);
        }
    }
    private void handleWateringPending(String message){
        //TODO process message, publish it
        log.info("GOING TO READ MESSAGE FROM TOPIC {}",topics.getPendingWatering());
        mqttGateway.sendToMqtt(message,topics.getPendingWatering());
        log.info("READ {} BYTES FROM {} TOPIC",message.length(),topics.getPendingWatering());
    }
}
