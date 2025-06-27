package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.entity.InternshipPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipPostDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String duration;
    private String requirements;
    private String salary;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date applicationDeadline;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdAt;

    private Long createdById;
    private String createdByName;
    private String companyName;

    public InternshipPostDTO(InternshipPost internshipPost) {
        this.id = internshipPost.getId();
        this.title = internshipPost.getTitle();
        this.description = internshipPost.getDescription();
        this.location = internshipPost.getLocation();
        this.duration = internshipPost.getDuration();
        this.requirements = internshipPost.getRequirements();
        this.salary = internshipPost.getSalary();
        this.applicationDeadline = internshipPost.getApplicationDeadline();
        this.createdAt = internshipPost.getCreatedAt();

        if (internshipPost.getCreatedBy() != null) {
            this.createdById = internshipPost.getCreatedBy().getId();
            this.createdByName = internshipPost.getCreatedBy().getName();
            this.companyName = internshipPost.getCreatedBy().getCompanyName();
        }
    }
}
