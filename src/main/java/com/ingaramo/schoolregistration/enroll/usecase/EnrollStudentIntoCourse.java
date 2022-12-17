package com.ingaramo.schoolregistration.enroll.usecase;

import com.ingaramo.schoolregistration.common.Pair;
import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollStudentIntoCourse {
    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;
    private final ValidateCourseEnroll validateCourseEnroll;

    public StudentDto execute(Integer courseId, Integer studentId) {
        Pair<Student, Course> studentCoursePair = validateCourseEnroll.execute(courseId, studentId);

        Student student = studentCoursePair.getFirst();
        Course course = studentCoursePair.getSecond();

        student.getStudentCourses().add(course);
        course.getStudents().add(student);

        return studentConverter.toDto(studentRepository.save(student));
    }
}
