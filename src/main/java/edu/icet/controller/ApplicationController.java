package edu.icet.controller;

import edu.icet.dto.ApplicationDTO;
import edu.icet.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> applyForInternship(@RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO savedApplication = applicationService.applyForInternship(applicationDTO);
            return ResponseEntity.ok(savedApplication);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudent(studentId));
    }

    @GetMapping("/internship/{internshipId}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByInternship(@PathVariable Long internshipId) {
        return ResponseEntity.ok(applicationService.getApplicationsByInternship(internshipId));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(applicationService.getApplicationsByCompany(companyId));
    }

    @PutMapping("/{applicationId}/status/{status}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(@PathVariable Long applicationId,
                                                                  @PathVariable String status) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(applicationId, status));
    }

    @GetMapping("/check/{studentId}/{internshipId}")
    public ResponseEntity<Boolean> hasAlreadyApplied(@PathVariable Long studentId,
                                                     @PathVariable Long internshipId) {
        return ResponseEntity.ok(applicationService.hasAlreadyApplied(studentId, internshipId));
    }
}
