package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetStudent {
    private final StudentRepository repository;
    private final StudentConverter converter;

    public StudentDto execute(Integer id) {
        return repository.findByIdAndStatus(id, true)
                .map(converter::toDto)
                .orElseThrow(() -> new InvalidStudentIdException("Invalid id provided: " + id));
    }
}
