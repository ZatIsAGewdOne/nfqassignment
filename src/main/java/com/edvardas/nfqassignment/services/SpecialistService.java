package com.edvardas.nfqassignment.services;

import com.edvardas.nfqassignment.entities.Specialist;
import com.edvardas.nfqassignment.repositories.SpecialistRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Specialist createSpecialist(Specialist specialist) {
        val foundSpecialist = specialistRepository.findByName(specialist.name);

        if (foundSpecialist.isPresent()) {
            LOGGER.info("Specialist already exists!");
        }

        return specialistRepository.save(specialist);
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
}
