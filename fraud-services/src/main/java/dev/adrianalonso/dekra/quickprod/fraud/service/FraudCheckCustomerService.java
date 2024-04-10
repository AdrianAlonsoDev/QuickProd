package dev.adrianalonso.dekra.quickprod.fraud.service;

import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.fraud.model.FraudCustomerCheck;
import dev.adrianalonso.dekra.quickprod.fraud.repository.FraudCustomerCheckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public record FraudCheckCustomerService(FraudCustomerCheckRepository fraudCustomerCheckRepository) {

    public boolean fraudCheckingCustomer(FraudRequest fraudRequest) {
        Optional<FraudCustomerCheck> fraudCustomerCheck = fraudCustomerCheckRepository
                .findAllByIdTypeAndIdValue(fraudRequest.idType(), fraudRequest.idValue());
        if(fraudCustomerCheck.isPresent()){
            return true;
        }
        fraudCustomerCheckRepository.save(
                FraudCustomerCheck.builder()
                        .customerKey(fraudRequest.idType()+fraudRequest.idValue())
                        .idType(fraudRequest.idType())
                        .idValue(fraudRequest.idValue())
                        .created(LocalDateTime.now())
                        .build()
        );
        return false;
    }

}
