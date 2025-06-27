package edu.icet.service.impl;

import edu.icet.dto.InternshipPostDTO;
import edu.icet.entity.InternshipPost;
import edu.icet.entity.User;
import edu.icet.repository.InternshipPostRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.InternshipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipPostServiceImpl implements InternshipPostService {

    private final InternshipPostRepository internshipPostRepository;
    private final UserRepository userRepository;

    @Override
    public InternshipPostDTO createInternship(InternshipPostDTO internshipPostDTO) {
        User user = userRepository.findById(internshipPostDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        InternshipPost internshipPost = new InternshipPost();
        internshipPost.setTitle(internshipPostDTO.getTitle());
        internshipPost.setDescription(internshipPostDTO.getDescription());
        internshipPost.setLocation(internshipPostDTO.getLocation());
        internshipPost.setDuration(internshipPostDTO.getDuration());
        internshipPost.setRequirements(internshipPostDTO.getRequirements());
        internshipPost.setSalary(internshipPostDTO.getSalary());
        internshipPost.setApplicationDeadline(internshipPostDTO.getApplicationDeadline());
        internshipPost.setCreatedBy(user);

        InternshipPost savedPost = internshipPostRepository.save(internshipPost);
        return new InternshipPostDTO(savedPost);
    }

    @Override
    public List<InternshipPostDTO> getInternshipsByCompany(Long companyId) {
        return internshipPostRepository.findByCreatedById(companyId).stream()
                .map(InternshipPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipPostDTO> getAllInternships() {
        return internshipPostRepository.findAllOrderByCreatedAtDesc().stream()
                .map(InternshipPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public InternshipPostDTO getInternshipById(Long id) {
        return internshipPostRepository.findById(id)
                .map(InternshipPostDTO::new)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
    }

    @Override
    public List<InternshipPostDTO> searchInternships(String title, String location) {
        return internshipPostRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(title, location)
                .stream()
                .map(InternshipPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public InternshipPostDTO updateInternship(Long id, InternshipPostDTO internshipPostDTO) {
        InternshipPost internshipPost = internshipPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        internshipPost.setTitle(internshipPostDTO.getTitle());
        internshipPost.setDescription(internshipPostDTO.getDescription());
        internshipPost.setLocation(internshipPostDTO.getLocation());
        internshipPost.setDuration(internshipPostDTO.getDuration());
        internshipPost.setRequirements(internshipPostDTO.getRequirements());
        internshipPost.setSalary(internshipPostDTO.getSalary());
        internshipPost.setApplicationDeadline(internshipPostDTO.getApplicationDeadline());

        InternshipPost updatedPost = internshipPostRepository.save(internshipPost);
        return new InternshipPostDTO(updatedPost);
    }

    @Override
    public void deleteInternship(Long id) {
        if (!internshipPostRepository.existsById(id)) {
            throw new RuntimeException("Internship not found");
        }
        internshipPostRepository.deleteById(id);
    }
}
