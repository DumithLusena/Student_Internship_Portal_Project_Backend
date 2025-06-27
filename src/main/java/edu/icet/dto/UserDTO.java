package edu.icet.dto;

import edu.icet.entity.User;
import edu.icet.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String companyName;
    private String course;
    private String institution;
    private String phone;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.companyName = user.getCompanyName();
        this.course = user.getCourse();
        this.institution = user.getInstitution();
        this.phone = user.getPhone();
    }
}
