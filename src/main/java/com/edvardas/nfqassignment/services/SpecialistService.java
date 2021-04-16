package com.edvardas.nfqassignment.services;

import com.edvardas.nfqassignment.entities.Specialist;
import com.edvardas.nfqassignment.repositories.SpecialistRepository;
import com.edvardas.nfqassignment.security.EntityNotFoundException;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService {

    @Autowired
    public SpecialistRepository specialistRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialistService.class);

    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    public Specialist getSpecialist(Integer id) {
        return specialistRepository.getOne(id);
    }

    public Optional<Specialist> createSpecialist(Specialist specialist) {
        val foundSpecialist = specialistRepository.findByName(specialist.name);

        if (foundSpecialist.isPresent()) {
            LOGGER.info("Specialist already exists!");
            return foundSpecialist;
        }

        specialistRepository.save(specialist);
        return Optional.of(specialist);
    }

    public void updateSpecialist(Specialist specialist) {
        val foundSpecialist = specialistRepository.getOne(specialist.id);

        foundSpecialist.setId(specialist.id);
        foundSpecialist.setName(specialist.getName());
        foundSpecialist.setPassword(specialist.getPassword());
        foundSpecialist.setCustomers(specialist.getCustomers());
        specialistRepository.save(foundSpecialist);
    }

    public boolean removeSpecialist(Integer id) {
        val specialist = specialistRepository.findById(id);

        if (specialist.isPresent()) {
            LOGGER.info("Specialst found! Deleting...");
            specialistRepository.delete(specialist.get());
            return true;
        }

        LOGGER.info("Specialist not found");
        return false;
    }

    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    public Specialist findSpecialistByName(String name) {
        val specialist = specialistRepository.findByName(name);

        if (specialist.isEmpty()) {
            throw new EntityNotFoundException(name);
        }

        return specialist.get();
    }

    public Optional<Specialist> findSpecialistByUsername(String username) {
        val specialist = specialistRepository.findByUsername(username);

        if (specialist.isEmpty()) {
            throw new EntityNotFoundException(username);
        }

        return specialist;
    }
}
