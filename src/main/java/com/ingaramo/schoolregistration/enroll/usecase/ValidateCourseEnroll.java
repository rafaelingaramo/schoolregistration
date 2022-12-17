package com.ingaramo.schoolregistration.enroll.usecase;

import com.ingaramo.schoolregistration.common.Pair;
import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import com.ingaramo.schoolregistration.enroll.exception.MaximumCoursesByStudentAchievedException;
import com.ingaramo.schoolregistration.enroll.exception.MaximumStudentsAchievedByCourseException;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateCourseEnroll {
    private static final String INVALID_STUDENT = "Invalid student id: ";
    private static final String INVALID_COURSE = "Invalid course id: ";

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    @Value("${application.maximumCoursesPerStudent:5}")
    protected Integer maximumCoursesPerStudent;
    @Value("${application.maximumStudentsPerCourse:50}")
    protected Integer maximumStudentsPerCourse;

    public Pair<Student, Course> execute(Integer courseId, Integer studentId) {
        Course course = validateCourseRules(courseId);
        Student student = validateStudentRules(studentId);

        Pair<Student, Course> pair = new Pair<>();
        pair.setFirst(student);
        pair.setSecond(course);
        return pair;
    }

    private Student validateStudentRules(Integer studentId) {
        Student student = studentRepository.findByIdAndStatus(studentId, true)
                .orElseThrow(() -> new InvalidStudentIdException(INVALID_STUDENT + studentId));

        Integer courseCount = studentRepository.countCoursesByStudent(studentId);

        if (courseCount >= maximumCoursesPerStudent) {
            throw new MaximumCoursesByStudentAchievedException(studentId);
        }

        return student;
    }

    private Course validateCourseRules(Integer courseId) {
        Course course = courseRepository.findByIdAndStatus(courseId, true)
                .orElseThrow(() -> new InvalidCourseIdException(INVALID_COURSE + courseId));

        Integer studentsCount = courseRepository.countStudentsByCourse(courseId);
        if (studentsCount >= maximumStudentsPerCourse) {
            throw new MaximumStudentsAchievedByCourseException(courseId);
        }
        return course;
    }
}
