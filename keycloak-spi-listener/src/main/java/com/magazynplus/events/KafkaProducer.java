package com.magazynplus.events;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducer {

    private final static String BOOTSTRAP_SERVER = "broker:29092";


    public static void publishEventRegister(String topic, NewUserRegisterDto newUser) {
        resetThreadContext();
        String VALUE_PROPERTIES = "com.magazynplus.events.Serializer";
        org.apache.kafka.clients.producer.KafkaProducer<String, NewUserRegisterDto> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(getProperties(VALUE_PROPERTIES));
        ProducerRecord<String, NewUserRegisterDto> eventRecord = new ProducerRecord<>(topic, newUser);
        producer.send(eventRecord);
        producer.close();
    }

    public static void publishEventLogin(String topic, String username) {
        resetThreadContext();
        String VALUE_PROPERTIES = StringSerializer.class.getName();
        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(getProperties(VALUE_PROPERTIES));
        ProducerRecord<String, String> eventRecord = new ProducerRecord<>(topic, username);
        producer.send(eventRecord);
        producer.close();

    }


    private static void resetThreadContext() {
        Thread.currentThread().setContextClassLoader(null);
    }

    public static Properties getProperties(String valueProperties) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "0");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueProperties);
        return properties;
    }

}
