package com.edvardas.nfqassignment.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "specialists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Specialist implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "username", nullable = false)
    public String username;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "first_name", length = 45)
    public String name;

    @Column(name = "last_name", length = 45)
    public String lastName;

    public String specialistId;

    public LocalDateTime registrationDate;

    @OneToMany(targetEntity = Customer.class, fetch = FetchType.LAZY, mappedBy = "specialist")
    @JsonBackReference
    public List<Customer> customers;

    public String role;

    public Specialist() {
        customers = new ArrayList<>();
        registrationDate = LocalDateTime.now();
        role = "SPECIALIST";
        specialistId = "#" + Math.round((Math.random() * 999) + 1);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
