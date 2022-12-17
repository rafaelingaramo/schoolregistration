package com.ingaramo.schoolregistration.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAllByStatus(Pageable pageable, boolean status);

    @Query(value = "SELECT s " +
            "         FROM student s" +
            "         JOIN s.studentCourses c" +
            "        WHERE c.id = :courseId" +
            "          AND s.status = :status " +
            "        ORDER BY s.name ")
    Page<Student> findAllByStatusAndCourse(@Param("status") boolean status,
                                           @Param("courseId") Integer courseName,
                                           Pageable pageable);

    @Query(value = " SELECT COUNT(distinct c.id)" +
            "          FROM student s" +
            "          JOIN student_course sc on sc.student_id = s.id " +
            "          JOIN course c on sc.course_id = c.id " +
            "         WHERE s.id = :studentId", nativeQuery = true)
    Integer countCoursesByStudent(@Param("studentId") Integer studentId);

    @Query(value = " SELECT s.id, s.name " +
            "          FROM student s " +
            "         WHERE NOT EXISTS (" +
            "           SELECT 1 " +
            "             FROM student_course sc " +
            "            WHERE sc.student_id = s.id " +
            "         ) "
            , nativeQuery = true)
    Page<StudentProjection> emptyCourseStudents(Pageable withPage);

    Optional<Student> findByIdAndStatus(Integer studentId, boolean status);
}
