
package com.scmuWateringSystem.wateringSystem.mqtt;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class MqttConfigs {

    //public static final String AUTO_WATERING_NOTIFICATION_TOPIC = "NOTIFICATION/AUTO_WATERING";
    //ublic static final String MANUAL_WATERING_NOTIFICATION_TOPIC = "NOTIFICATION/MANUAL_WATERING";

    @Value( "${mqtt.url}" )
    private String url;

    @Value( "${mqtt.receiverClientId}" )
    private String clientReceiverId;

    @Value( "${mqtt.senderClientId}" )
    private String clientSenderId;
    @Autowired
    private Topics topics;

    @Autowired
    private ReceivedMessagesServiceHandler receivedMessagesServiceHandler;

        @Bean
        public MqttPahoClientFactory mqttClientFactory() {
            DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(true);

            //String url = "tcp://localhost:1883";
            options.setServerURIs(new String[] { url});
            //options.setUserName("admin");
            //String pass = "12345678";
            //options.setPassword(pass.toCharArray());
            options.setCleanSession(true);
            factory.setConnectionOptions(options);
            return factory;
        }
        public void readFromLuminosityData() throws MqttException {
            MqttPahoClientFactory pahoClientFactory = mqttClientFactory();
            IMqttClient iMqttClient = pahoClientFactory.getClientInstance(url,clientSenderId);
            iMqttClient.subscribeWithResponse(topics.getLuminosityData(), (tpic, msg) -> {
                System.out.println(msg.getId() + " -> " + new String(msg.getPayload()));
            });
        }
        @Bean
        public MessageChannel mqttInputChannel() {
            return new DirectChannel();
        }

        @Bean
        public MessageProducer inbound() throws Exception{
            /**
             * Subscribe:
             * humidity/data -> MetricsService lightData
             * temperature/data -> MetricsService.TemperatureData
             * luminosity/data -> MetricsService.luminosityData
             * watering/event -
             */
            String allTopics = "#";
            MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientReceiverId,
                    mqttClientFactory(),
                    topics.getHumidityData(),
                    topics.getTemperatureData(),
                    topics.getLuminosityData(),
                    topics.getWateringEvent());

            adapter.setCompletionTimeout(5000);
            adapter.setConverter(new DefaultPahoMessageConverter());
            adapter.setQos(2);
            adapter.setOutputChannel(mqttInputChannel());
            return adapter;
        }



        @Bean
        @ServiceActivator(inputChannel = "mqttInputChannel")
        public MessageHandler handler() throws MqttException {
            return new MessageHandler() {
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                    String strMessage = message.getPayload().toString();
                    receivedMessagesServiceHandler.handleAllTopics(topic,strMessage);
                }
            };
        }


        @Bean
        public MessageChannel mqttOutboundChannel() {
            return new DirectChannel();
        }
        @Bean
        @ServiceActivator(inputChannel = "mqttOutboundChannel")
        public MessageHandler mqttOutbound() throws MqttException {
            //clientId is generated using a random number
            MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientSenderId, mqttClientFactory());
            messageHandler.setAsync(true);
            messageHandler.setDefaultTopic("testTopic");
            messageHandler.setDefaultRetained(false);

            return messageHandler;
        }
}