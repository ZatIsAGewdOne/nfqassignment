package com.edvardas.nfqassignment.api;

import com.edvardas.nfqassignment.entities.Specialist;
import com.edvardas.nfqassignment.services.SpecialistService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/specialist")
public class SpecialistAPI {

    @Autowired
    public SpecialistService specialistService;

    public SpecialistAPI(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @PostMapping
    public ResponseEntity<Specialist> createSpecialist(@RequestBody Specialist specialist) {
        specialistService.createSpecialist(specialist);
        return ResponseEntity.ok(specialist);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Specialist> getSpecialist(@PathVariable int id) {
        val specialist = specialistService.getSpecialist(id);

        if (specialist != null) {
            return ResponseEntity.ok(specialist);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
