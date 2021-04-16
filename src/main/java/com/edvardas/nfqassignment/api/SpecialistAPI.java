package com.edvardas.nfqassignment.api;

import com.edvardas.nfqassignment.entities.Customer;
import com.edvardas.nfqassignment.entities.Specialist;
import com.edvardas.nfqassignment.security.EntityNotFoundException;
import com.edvardas.nfqassignment.services.SpecialistService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        val result = specialistService.createSpecialist(specialist);
        return ResponseEntity.of(result);
    }

    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Specialist> login(HttpServletRequest request, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            val session = request.getSession();
            val specialist = specialistService.findSpecialistByUsername(username);
            if (specialist.isPresent() && specialist.get().getPassword().equals(password)) {
                val spec = specialist.get();
                request.login(spec.getUsername(), spec.getPassword());
                session.setAttribute("username", username);
                return ResponseEntity.ok(spec);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        val session = request.getSession();
        if (session.getAttribute("username") != null) {
            try {
                request.logout();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Specialist> getSpecialist(@PathVariable int id) {
        val specialist = specialistService.getSpecialist(id);

        if (specialist != null) {
            return ResponseEntity.ok(specialist);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('SPECIALIST')")
    @GetMapping("all")
    public ResponseEntity<List<Specialist>> getAllSpecialists() {
        val specialists = specialistService.getAllSpecialists();

        if (specialists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(specialists);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/customers")
    public ResponseEntity<List<Customer>> getCustomersBySpecialist(@PathVariable int id) {
        val specialist = specialistService.getSpecialist(id);

        if (specialist != null) {
            return ResponseEntity.ok(specialist.getCustomers());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
