package com.univault_ucs.Repository;

import com.univault_ucs.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByNameAndInstituteId(String name, UUID id);
    List<Course> findAllCoursesByInstituteId(UUID id);
}
