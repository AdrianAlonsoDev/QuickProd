package dev.adrianalonso.dekra.quickprod.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "dev.adrianalonso.dekra.quickprod.customer",
                "dev.adrianalonso.dekra.quickprod.rabbitmq"
        }
)
@EnableFeignClients(basePackages = "dev.adrianalonso.dekra.quickprod.clients")
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}