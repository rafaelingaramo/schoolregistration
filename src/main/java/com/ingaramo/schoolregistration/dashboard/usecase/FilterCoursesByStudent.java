package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterCoursesByStudent {
    private final StudentRepository repository;
    private final CourseConverter courseConverter;

    public Set<CourseDto> execute(Integer studentId) {
        Student student = repository.findByIdAndStatus(studentId, true)
                .orElseThrow(() -> new InvalidStudentIdException("Invalid id provided"));

        if (student.getStudentCourses() == null || student.getStudentCourses().isEmpty()) {
            return Collections.emptySet();
        }

        return student.getStudentCourses().stream()
                .map(courseConverter::toDto)
                .collect(Collectors.toSet());
    }
}
