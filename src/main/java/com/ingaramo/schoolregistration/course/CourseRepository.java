package com.ingaramo.schoolregistration.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findAllByStatus(Pageable pageable, boolean status);
}
