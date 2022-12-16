package com.ingaramo.schoolregistration.student.exception.usecase;

import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveStudent {
    private final StudentRepository repository;
    private final StudentConverter converter;

    public StudentDto execute(@NonNull StudentDto studentDto) {
        if (studentDto.getName() == null || studentDto.getName().isEmpty()) {
            throw new InvalidStudentException(studentDto);
        }

        return converter.toDto(repository.save(converter.toNewEntity(studentDto)));
    }
}
