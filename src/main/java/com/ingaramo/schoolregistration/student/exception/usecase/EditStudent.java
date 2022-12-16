package com.ingaramo.schoolregistration.student.exception.usecase;

import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentException;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditStudent {
    private final StudentRepository repository;
    private final StudentConverter converter;

    public StudentDto execute(StudentDto studentDto) {
        if (studentDto.getName() == null || studentDto.getName().isEmpty()) {
            throw new InvalidStudentException(studentDto);
        }
        if (studentDto.getId() == null) {
            throw new InvalidStudentException(studentDto);
        }

        Optional<Student> optionalStudent = repository.findById(studentDto.getId());
        Student student = optionalStudent
                .orElseThrow(() -> new InvalidStudentIdException("Invalid id: " + studentDto.getId()));
        student.setName(studentDto.getName());
        return converter.toDto(repository.save(student));
    }
}
