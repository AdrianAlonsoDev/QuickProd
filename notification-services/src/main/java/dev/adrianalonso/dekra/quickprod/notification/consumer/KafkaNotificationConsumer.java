package dev.adrianalonso.dekra.quickprod.notification.consumer;

import dev.adrianalonso.dekra.quickprod.clients.notification.NotificationRequest;
import dev.adrianalonso.dekra.quickprod.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification", groupId = "notification-id", containerFactory = "customKafkaFactory")
    public void kafkaConsumer(NotificationRequest notificationRequest, Acknowledgment acknowledgment) {
        log.info("Kafka Consumed {} from queue", notificationRequest);
        notificationService.send(notificationRequest);
        acknowledgment.acknowledge();
    }

}
