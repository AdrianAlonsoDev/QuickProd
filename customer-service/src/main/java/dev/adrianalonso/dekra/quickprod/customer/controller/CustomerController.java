package dev.adrianalonso.dekra.quickprod.customer.controller;

import dev.adrianalonso.dekra.quickprod.customer.data.CustomerRegistrationRequest;
import dev.adrianalonso.dekra.quickprod.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public record CustomerController (CustomerService customerService)
{

    @GetMapping
    public String getCustomers() {
        log.info("Intercept request to get all customers");
        return customerService.getCustomers();
    }
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest registrationRequest) {
        log.info("Intercept request to register new customer: {} ", registrationRequest);
        customerService.registerCustomer(registrationRequest);
    }
}
