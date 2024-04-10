package dev.adrianalonso.dekra.quickprod.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudCheckResponse;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudClient;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.clients.notification.NotificationClient;
import dev.adrianalonso.dekra.quickprod.clients.notification.NotificationRequest;
import dev.adrianalonso.dekra.quickprod.customer.data.CustomerRegistrationRequest;
import dev.adrianalonso.dekra.quickprod.customer.model.Customer;
import dev.adrianalonso.dekra.quickprod.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    private final KafkaTemplate<String, Object> customKafkaTemplate;
    public void registerCustomer(CustomerRegistrationRequest customerRequest) {

        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .gender(customerRequest.gender())
                .idType(customerRequest.idType())
                .idValue(customerRequest.idValue())
                .phoneNumber(customerRequest.phoneNumber())
                .build();

        FraudRequest fraudRequest = new FraudRequest(
                customerRequest.idType(),
                customerRequest.idValue()
        );

        FraudCheckResponse fraudCustomerCheckResponse = fraudClient.checkCustomer(fraudRequest);
        if(null != fraudCustomerCheckResponse && fraudCustomerCheckResponse.isFraudulentCustomer()){
            log.warn("Customer ID {} is fraudster", customer.getId());
            throw new IllegalStateException("Fraudster");
        }

        customerRepository.saveAndFlush(customer);

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Rean Code...",
                        customer.getFirstName() + customer.getLastName())
        );

        customKafkaTemplate.send("notification", notificationRequest);
    }

}