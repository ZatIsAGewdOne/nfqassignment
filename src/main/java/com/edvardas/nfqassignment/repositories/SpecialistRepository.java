package com.edvardas.nfqassignment.repositories;

import com.edvardas.nfqassignment.entities.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

    Optional<Specialist> findByName(String name);
}
