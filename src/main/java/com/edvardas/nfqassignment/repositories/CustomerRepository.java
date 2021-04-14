package com.edvardas.nfqassignment.repositories;

import com.edvardas.nfqassignment.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
