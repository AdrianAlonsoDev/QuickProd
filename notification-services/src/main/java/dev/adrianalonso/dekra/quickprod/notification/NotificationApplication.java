package dev.adrianalonso.dekra.quickprod.notification;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "dev.adrianalonso.dekra.quickprod.notification",
                "dev.adrianalonso.dekra.quickprod.rabbitmq"
        }
)
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    /*
    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer rabbitMQMessageProducer,
                                        NotificationConfig notificationConfig) {
        return args -> {
            rabbitMQMessageProducer.publish(
                    new NotificationRequest(1, "Theara", "Hi Theara, welcome to ..."),
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey()
            );
        };
    }
     */

}
