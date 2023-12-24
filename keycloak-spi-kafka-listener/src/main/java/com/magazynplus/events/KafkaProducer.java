package com.magazynplus.events;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducer {

    private final static String BOOTSTRAP_SERVER = "broker:29092";


    public static void publishEvent(String topic, NewUserRegisterDto newUser){
        resetThreadContext();
        org.apache.kafka.clients.producer.KafkaProducer<String, NewUserRegisterDto> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(getProperties());
        ProducerRecord<String, NewUserRegisterDto> eventRecord = new ProducerRecord<>(topic, newUser);
        producer.send(eventRecord);
        producer.close();
    }

    private static void resetThreadContext() {
        Thread.currentThread().setContextClassLoader(null);
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "0");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.magazynplus.events.Serializer");
        return properties;
    }

}
