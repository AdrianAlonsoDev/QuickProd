package dev.adrianalonso.dekra.quickprod.fraud.controller;

import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudCheckResponse;
import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.fraud.service.FraudCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-checks")
@Slf4j
public record FraudController(FraudCustomerService fraudCustomerService) {


    @PostMapping()
    public FraudCheckResponse checkFraudCustomer(@RequestBody FraudRequest fraudRequest) {
        log.info("Intercept customer fraud {}", fraudRequest);
        boolean isFraudulentCustomer = fraudCustomerService.
                checkingCustomerInfo(fraudRequest);


        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
