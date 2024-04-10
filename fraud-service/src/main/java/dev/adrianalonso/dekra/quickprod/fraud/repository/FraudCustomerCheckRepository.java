package dev.adrianalonso.dekra.quickprod.fraud.repository;

import dev.adrianalonso.dekra.quickprod.fraud.model.FraudCustomerCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface FraudCustomerCheckRepository extends JpaRepository<FraudCustomerCheck, Integer> {
    Optional<FraudCustomerCheck> findAllByIdTypeAndIdValue(String idType, String idValue);
}
