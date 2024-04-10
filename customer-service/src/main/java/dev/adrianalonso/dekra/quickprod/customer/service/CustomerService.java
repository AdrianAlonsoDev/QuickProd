package dev.adrianalonso.dekra.quickprod.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.adrianalonso.dekra.quickprod.rabbitmq.RabbitMQMessageProducer;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudCheckResponse;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudClient;
import dev.adrianalonso.dekra.quickprod.customer.data.CustomerRegistrationRequest;
import dev.adrianalonso.dekra.quickprod.customer.model.Customer;
import dev.adrianalonso.dekra.quickprod.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;

    private RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    public void  registerCustomer(CustomerRegistrationRequest registrationRequest) {

        Customer customer = Customer.builder()
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .gender(registrationRequest.gender())
                .email(registrationRequest.email())
                .phoneNumber(registrationRequest.phoneNumber())
                .idType(registrationRequest.idType())
                .idValue(registrationRequest.idValue())
                .createdAt(LocalDateTime.now())
                .build();

        FraudRequest fraudRequest = new FraudRequest(
                registrationRequest.idType(),
                registrationRequest.idValue()
        );

        FraudCheckResponse fraudCheckResponse = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();

            var fraudRequestJson = objectMapper.writeValueAsString(fraudRequest);
            HttpEntity<Object> request = new HttpEntity<>(fraudRequestJson, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:8081/api/v1/fraud-checks",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            log.info("Fraud check response: {}", responseEntity.getBody());
            fraudCheckResponse = objectMapper.readValue(responseEntity.getBody(), FraudCheckResponse.class);
        } catch (Exception e) {
            log.error("Error while checking fraud", e);
        }

        //fraudCheckResponse = fraudClient.checkingCustomerInfo(fraudRequest);

        if(null != fraudCheckResponse && fraudCheckResponse.isFraudulentCustomer()){
            log.warn("Customer ID {} is fraudster", customer.getId());
            throw new IllegalStateException("Fraudster");
        }

        customerRepository.saveAndFlush(customer);
        log.info("Customer registered successfully, CUSTOMER INFO: {}", customer);
    }

    public String getCustomers() {
        log.info("Getting all customers");
        return customerRepository.findAll().toString();
    }

}
