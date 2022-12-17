package com.ingaramo.schoolregistration.student;

import com.ingaramo.schoolregistration.course.CourseConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentConverter {
    private final CourseConverter courseConverter;

    public StudentDto toDto(@NonNull Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .courses(student.getStudentCourses()
                        .stream().map(courseConverter::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public StudentDto toDto(@NonNull StudentProjection student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .build();
    }

    public Student toNewEntity(StudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .status(true)
                .build();
    }

    public Student toEntity(StudentDto studentDto) {
        return Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .status(true)
                .build();
    }
}
