package edu.icet.service;

import edu.icet.dto.InternshipPostDTO;

import java.util.List;

public interface InternshipPostService {
    InternshipPostDTO createInternship(InternshipPostDTO internshipPostDTO);
    List<InternshipPostDTO> getInternshipsByCompany(Long companyId);
    List<InternshipPostDTO> getAllInternships();
    InternshipPostDTO getInternshipById(Long id);
    List<InternshipPostDTO> searchInternships(String title, String location);
    InternshipPostDTO updateInternship(Long id, InternshipPostDTO internshipPostDTO);
    void deleteInternship(Long id);
}
