package com.edvardas.nfqassignment.services;

import com.edvardas.nfqassignment.entities.Customer;
import com.edvardas.nfqassignment.repositories.CustomerRepository;
import com.edvardas.nfqassignment.security.EntityNotFoundException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public SpecialistService specialistService;

    public CustomerService(CustomerRepository customerRepository, SpecialistService specialistService) {
        this.customerRepository = customerRepository;
        this.specialistService = specialistService;
    }

    public Customer findCustomerById(Integer id) {
        return customerRepository.getOne(id);
    }

    public Customer createCustomer(Customer customer) {
        val specialist = customer.getSpecialist();
        specialist.getCustomers().add(customer);
        specialistService.updateSpecialist(specialist);
        return customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        val foundCustomer = customerRepository.getOne(customer.id);
        foundCustomer.setActive(customer.isActive());
        foundCustomer.setCreationDate(customer.getCreationDate());
        foundCustomer.setSpecialist(customer.getSpecialist());
        foundCustomer.setId(customer.getId());
        foundCustomer.setRole(customer.getRole());
        foundCustomer.setRegistrationKey(customer.getRegistrationKey());
        customerRepository.save(foundCustomer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerByRegistrationKey(String key) {
        val foundCustomer = customerRepository.findByRegistrationKey(key);

        if (foundCustomer.isEmpty()) {
            throw new EntityNotFoundException(key);
        }

        return foundCustomer.get();
    }
}
