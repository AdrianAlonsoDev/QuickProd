package dev.adrianalonso.dekra.quickprod.customer.repository;

import dev.adrianalonso.dekra.quickprod.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findAllById(Integer id);
}