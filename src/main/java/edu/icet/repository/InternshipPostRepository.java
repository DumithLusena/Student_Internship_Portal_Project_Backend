package edu.icet.repository;

import edu.icet.entity.InternshipPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipPostRepository extends JpaRepository<InternshipPost, Long> {
    List<InternshipPost> findByCreatedById(Long createdById);

    @Query("SELECT i FROM InternshipPost i WHERE " +
            "(:title IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:location IS NULL OR LOWER(i.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<InternshipPost> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            @Param("title") String title, @Param("location") String location);

    @Query("SELECT i FROM InternshipPost i ORDER BY i.createdAt DESC")
    List<InternshipPost> findAllOrderByCreatedAtDesc();
}
