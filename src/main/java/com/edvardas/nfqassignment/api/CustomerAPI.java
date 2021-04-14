package com.edvardas.nfqassignment.api;

import com.edvardas.nfqassignment.entities.Customer;
import com.edvardas.nfqassignment.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {
    @Autowired
    public CustomerService customerService;

    public CustomerAPI(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
