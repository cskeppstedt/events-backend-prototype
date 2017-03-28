package com.mapr.examples;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Properties;
import java.util.UUID;

/**
 * This producer will send a bunch of messages to topic "fast-messages". Every so often,
 * it will send a message to "slow-messages". This shows how messages can be sent to
 * multiple topics. On the receiving end, we will see both kinds of messages but will
 * also see how the two topics aren't really synchronized.
 */

// api_setup
// fullscreen_iframe_loaded
// fullscreen_iframe_created
// fullscreen_iframe_timed_out
// main_iframe_created
// main_iframe_loaded
// main_iframe_visible
// main_iframe_timed_out
// dr_iframe_loaded
// dr_iframe_created
// dr_iframe_timed_out
public class Producer {
    public static void main(String[] args) throws IOException {
        // set up the producer
        KafkaProducer<String, String> producer;
        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }

        try {
            while (true) {
                String sessionId = UUID.randomUUID().toString();
                String instanceId = "" + ThreadLocalRandom.current().nextInt(1000, 10000);

                sendEvent(producer, "main_iframe_created", sessionId, instanceId);
                sendEvent(producer, "main_iframe_loaded", sessionId, instanceId);
                sendEvent(producer, "fullscreen_iframe_created", sessionId, instanceId);
                sendEvent(producer, "fullscreen_iframe_loaded", sessionId, instanceId);
                sendEvent(producer, "main_iframe_visible", sessionId, instanceId);
                sendEvent(producer, "dr_iframe_created", sessionId, instanceId);
                sendEvent(producer, "dr_iframe_loaded", sessionId, instanceId);

                if (ThreadLocalRandom.current().nextInt(0, 11) > 8) {
                    sendEvent(producer, "fullscreen_iframe_timed_out", sessionId, instanceId);
                }
                if (ThreadLocalRandom.current().nextInt(0, 11) > 9) {
                    sendEvent(producer, "main_iframe_timed_out", sessionId, instanceId);
                }
                if (ThreadLocalRandom.current().nextInt(0, 11) > 5) {
                    sendEvent(producer, "dr_iframe_timed_out", sessionId, instanceId);
                }
            }
        } catch (Exception e) {
            // System.out.printf(throwable.getStackTrace().toString());
            System.out.println(e.getMessage());
        } finally {
            producer.close();
        }
    }

    public static void sendEvent (KafkaProducer<String, String> producer, String name, String sessionId, String instanceId) throws InterruptedException {
        String message = String.format("{" +
            "\"type\":\"frontend-event\"," +
            "\"timestamp\":%d," +
            "\"name\":\"%s\"," +
            "\"host\":\"the-producer\"," +
            "\"service\":\"frontend-events/%s\"," +
            "\"state\":\"ok\"," +
            "\"tags\":[\"frontend-events\"]," +
            "\"sid\":\"%s\"," +
            "\"iid\":\"%s\"" +
            "}",
            System.currentTimeMillis(),
            name,
            name,
            sessionId,
            instanceId
        );

        System.out.println("Sending message: " + message);
        producer.send(new ProducerRecord<String, String>("events", message));

        int randomNum = ThreadLocalRandom.current().nextInt(500, 5000);
        Thread.sleep(randomNum);
    }
}
