package com.edvardas.nfqassignment.api;

import com.edvardas.nfqassignment.entities.Customer;
import com.edvardas.nfqassignment.security.EntityNotFoundException;
import com.edvardas.nfqassignment.services.CustomerService;
import com.edvardas.nfqassignment.services.SpecialistService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {
    @Autowired
    public CustomerService customerService;
    @Autowired
    public SpecialistService specialistService;

    public CustomerAPI(CustomerService customerService, SpecialistService specialistService) {
        this.customerService = customerService;
        this.specialistService = specialistService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer, HttpServletResponse response) {
        try {
            val specialist = specialistService.findSpecialistByName(customer.specialist.getName());
            customer.setSpecialist(specialist);
            customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        val customer = customerService.findCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        val customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customers);
    }
}
