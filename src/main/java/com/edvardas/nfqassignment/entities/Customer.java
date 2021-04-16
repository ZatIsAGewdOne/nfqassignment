package com.edvardas.nfqassignment.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "visit_id", unique = true)
    public String registrationKey;

    @Column(name = "creation_date")
    public LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist")
    @JsonManagedReference
    public Specialist specialist;

    public String role;

    @Column(name = "active")
    public boolean isActive;

    public Customer() {
        registrationKey = "#" + Math.round((Math.random() * Integer.MAX_VALUE) + 1);
        creationDate = LocalDateTime.now();
        role = "CUSTOMER";
    }
}
