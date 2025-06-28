package edu.icet.service.impl;

import edu.icet.dto.ApplicationDTO;
import edu.icet.entity.Application;
import edu.icet.entity.InternshipPost;
import edu.icet.entity.User;
import edu.icet.repository.ApplicationRepository;
import edu.icet.repository.InternshipPostRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.ApplicationService;
import edu.icet.service.EmailService;
import edu.icet.util.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final InternshipPostRepository internshipPostRepository;
    private final EmailService emailService;

    @Override
    public ApplicationDTO applyForInternship(ApplicationDTO applicationDTO) {
        if (hasAlreadyApplied(applicationDTO.getStudentId(), applicationDTO.getInternshipPostId())) {
            throw new RuntimeException("Already applied for this internship");
        }

        User student = userRepository.findById(applicationDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        InternshipPost internshipPost = internshipPostRepository.findById(applicationDTO.getInternshipPostId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application application = new Application();
        application.setResumeLink(applicationDTO.getResumeLink());
        application.setCoverLetter(applicationDTO.getCoverLetter());
        application.setStudent(student);
        application.setInternshipPost(internshipPost);

        Application savedApplication = applicationRepository.save(application);

        try {
            emailService.sendApplicationNotification(
                    internshipPost.getCreatedBy().getEmail(),
                    student.getName(),
                    internshipPost.getTitle()
            );
        } catch (Exception e) {
            log.error("Failed to send email notification: {}", e.getMessage());
        }

        return new ApplicationDTO(savedApplication);
    }

    @Override
    public List<ApplicationDTO> getApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId).stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDTO> getApplicationsByInternship(Long internshipId) {
        return applicationRepository.findByInternshipPostId(internshipId).stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDTO> getApplicationsByCompany(Long companyId) {
        return applicationRepository.findByInternshipPostCreatedById(companyId).stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO updateApplicationStatus(Long applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        try {
            ApplicationStatus newStatus = ApplicationStatus.valueOf(status.toUpperCase());
            application.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid application status: " + status);
        }

        Application updatedApplication = applicationRepository.save(application);

        try {
            emailService.sendStatusUpdateNotification(
                    application.getStudent().getEmail(),
                    application.getInternshipPost().getTitle(),
                    status
            );
        } catch (Exception e) {
            log.error("Failed to send status update email: {}", e.getMessage());
        }

        return new ApplicationDTO(updatedApplication);
    }

    @Override
    public Boolean hasAlreadyApplied(Long studentId, Long internshipId) {
        return applicationRepository.existsByStudentIdAndInternshipPostId(studentId, internshipId);
    }
}
