package com.edvardas.nfqassignment.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "specialists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "username", nullable = false)
    public String username;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "first_name", length = 45)
    public String name;

    @Column(name = "last_name", length = 45)
    public String lastName;

    @OneToMany(targetEntity = Customer.class, fetch = FetchType.LAZY, mappedBy = "specialist")
    public List<Customer> customers;

    public String role;

    public Specialist() {
        customers = new ArrayList<>();
        role = "SPECIALIST";
    }
}
