package org.acme;

import com.example.kafka.avro.ExampleMessage;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Path("/produce")
@Slf4j
@Singleton  // Ensure only Singleton is present as the scope annotation
public class Producer {
    @Inject
    @Channel("generated-messages")
    Emitter<ExampleMessage> emitter;

    private final String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah"};

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String startProducing() {
        return "Message production started. Check Kafka topic.";
    }

    @Scheduled(every = "5s")
    void produceMessage() {
        ExampleMessage message = ExampleMessage.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(getRandomName())
                .setTimestamp(Instant.now().toEpochMilli())
                .build();

        emitter.send(message);
        System.out.println("Message sent: " + message);
    }

    private String getRandomName() {
        int index = ThreadLocalRandom.current().nextInt(names.length);
        return names[index];
    }
}
