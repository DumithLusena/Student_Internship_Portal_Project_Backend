package edu.icet.repository;

import edu.icet.entity.Application;
import edu.icet.util.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByInternshipPostId(Long internshipPostId);
    List<Application> findByStatus(ApplicationStatus status);
    @Query("SELECT a FROM Application a WHERE a.internshipPost.createdBy.id = ?1")
    List<Application> findByInternshipPostCreatedById(Long companyId);
    Optional<Application> findByStudentIdAndInternshipPostId(Long studentId, Long internshipPostId);
    Boolean existsByStudentIdAndInternshipPostId(Long studentId, Long internshipPostId);
}
