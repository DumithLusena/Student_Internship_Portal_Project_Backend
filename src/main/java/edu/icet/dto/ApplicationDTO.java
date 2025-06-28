package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.entity.Application;
import edu.icet.util.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long id;
    private String resumeLink;
    private String coverLetter;
    private ApplicationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime appliedAt;

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long internshipPostId;
    private String internshipTitle;
    private String companyName;

    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.resumeLink = application.getResumeLink();
        this.coverLetter = application.getCoverLetter();
        this.status = application.getStatus();
        this.appliedAt = application.getAppliedAt();

        if (application.getStudent() != null) {
            this.studentId = application.getStudent().getId();
            this.studentName = application.getStudent().getName();
            this.studentEmail = application.getStudent().getEmail();
        }

        if (application.getInternshipPost() != null) {
            this.internshipPostId = application.getInternshipPost().getId();
            this.internshipTitle = application.getInternshipPost().getTitle();
            if (application.getInternshipPost().getCreatedBy() != null) {
                this.companyName = application.getInternshipPost().getCreatedBy().getCompanyName();
            }
        }
    }
}
