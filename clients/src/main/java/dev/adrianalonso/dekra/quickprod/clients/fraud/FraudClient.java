package dev.adrianalonso.dekra.quickprod.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fraud-service")
public interface FraudClient {
    @PostMapping(path = "api/v1//fraud-checks")
    FraudCheckResponse checkingCustomerInfo(@RequestBody FraudRequest fraudRequest);
}
