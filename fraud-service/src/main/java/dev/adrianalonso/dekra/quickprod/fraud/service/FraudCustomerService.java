package dev.adrianalonso.dekra.quickprod.fraud.service;

import dev.adrianalonso.dekra.quickprod.clients.fraud.FraudRequest;
import dev.adrianalonso.dekra.quickprod.fraud.model.FraudCustomerCheck;
import dev.adrianalonso.dekra.quickprod.fraud.repository.FraudCustomerCheckRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public record FraudCustomerService (FraudCustomerCheckRepository fraudCustomerCheckRepository){

    public boolean checkingCustomerInfo(FraudRequest fraudRequest) {

        Optional<FraudCustomerCheck> fraudCustomer = fraudCustomerCheckRepository
                .findAllByIdTypeAndIdValue(fraudRequest.idType(), fraudRequest.idValue());


        if (fraudCustomer.isPresent()) {
            log.warn("Customer info with idType: {} and idValue: {} already exists", fraudRequest.idType(), fraudRequest.idValue());
            return true;
        }

        fraudCustomerCheckRepository.save(FraudCustomerCheck.builder()
                        .customerKey(fraudRequest.idType()+fraudRequest.idValue())
                        .idType(fraudRequest.idType())
                        .idValue(fraudRequest.idValue())
                        .created(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}
