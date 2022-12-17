package com.ingaramo.schoolregistration.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findAllByStatus(Pageable pageable, Boolean status);

    @Query(value = " SELECT COUNT(distinct s.id)" +
            "          FROM course c" +
            "          JOIN student_course sc on sc.course_id = c.id " +
            "          JOIN student s on sc.student_id = s.id " +
            "         WHERE c.id = :courseId", nativeQuery = true)
    Integer countStudentsByCourse(@Param("courseId") Integer courseId);

    Optional<Course> findByIdAndStatus(Integer id, Boolean status);

    @Query(value = " SELECT c.id, c.name " +
            "          FROM course c " +
            "         WHERE NOT EXISTS (" +
            "           SELECT 1 " +
            "             FROM student_course sc " +
            "            WHERE sc.course_id = c.id " +
            "         ) "
            , nativeQuery = true)
    Page<CourseProjection> emptyStudentCourses(Pageable pageable);
}
