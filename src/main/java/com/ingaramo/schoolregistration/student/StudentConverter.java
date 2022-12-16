package com.ingaramo.schoolregistration.student;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class StudentConverter {
    public StudentDto toDto(@NonNull Student student) {
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
                .build();
    }
}
