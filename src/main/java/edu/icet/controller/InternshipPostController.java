package edu.icet.controller;

import edu.icet.dto.InternshipPostDTO;
import edu.icet.service.InternshipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class InternshipPostController {

    private final InternshipPostService internshipPostService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<InternshipPostDTO> createInternship(@RequestBody InternshipPostDTO internshipPostDTO) {
        return ResponseEntity.ok(internshipPostService.createInternship(internshipPostDTO));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<List<InternshipPostDTO>> getInternshipsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(internshipPostService.getInternshipsByCompany(companyId));
    }

    @GetMapping
    public ResponseEntity<List<InternshipPostDTO>> getAllInternships() {
        return ResponseEntity.ok(internshipPostService.getAllInternships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipPostDTO> getInternshipById(@PathVariable Long id) {
        return ResponseEntity.ok(internshipPostService.getInternshipById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<InternshipPostDTO>> searchInternships(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location) {
        return ResponseEntity.ok(internshipPostService.searchInternships(title, location));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<InternshipPostDTO> updateInternship(@PathVariable Long id,
                                                              @RequestBody InternshipPostDTO internshipPostDTO) {
        return ResponseEntity.ok(internshipPostService.updateInternship(id, internshipPostDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        internshipPostService.deleteInternship(id);
        return ResponseEntity.ok().build();
    }
}
