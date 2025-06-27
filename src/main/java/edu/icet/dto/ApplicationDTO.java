package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.util.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long id;
    private String resumeLink;
    private String coverLetter;
    private ApplicationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date appliedAt;

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long internshipPostId;
    private String internshipTitle;
    private String companyName;
}
