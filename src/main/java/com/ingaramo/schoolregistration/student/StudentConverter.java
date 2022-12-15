package com.ingaramo.schoolregistration.student;

import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

public interface StudentConverter {
    default Collection<StudentDto> toDtoList(@NonNull Collection<Student> studentCollection) {
        return studentCollection.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    default StudentDto toDto(@NonNull Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .build();
    }
}
