package dev.adrianalonso.dekra.quickprod.fraud.controller;

import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudCheckResponse;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.fraud.service.FraudCheckCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-checks")
@Slf4j
public record FraudController(FraudCheckCustomerService fraudCheckCustomerService) {


    @PostMapping()
    public FraudCheckResponse checkCustomer(@RequestBody FraudRequest fraudRequest) {
        boolean isFraudulentCustomer = fraudCheckCustomerService.
                fraudCheckingCustomer(fraudRequest);
        log.info("fraud check request for customer {}", isFraudulentCustomer);

        return new FraudCheckResponse(isFraudulentCustomer);
    }

}