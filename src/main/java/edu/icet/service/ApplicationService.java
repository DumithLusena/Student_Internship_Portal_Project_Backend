package edu.icet.service;

import edu.icet.dto.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO applyForInternship(ApplicationDTO applicationDTO);
    List<ApplicationDTO> getApplicationsByStudent(Long studentId);
    List<ApplicationDTO> getApplicationsByInternship(Long internshipId);
    List<ApplicationDTO> getApplicationsByCompany(Long companyId);
    ApplicationDTO updateApplicationStatus(Long applicationId, String status);
    Boolean hasAlreadyApplied(Long studentId, Long internshipId);
}
