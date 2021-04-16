package com.edvardas.nfqassignment.repositories;

import com.edvardas.nfqassignment.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByRegistrationKey(String registrationKey);
}
