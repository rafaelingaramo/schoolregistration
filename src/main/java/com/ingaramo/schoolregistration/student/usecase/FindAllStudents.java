package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllStudents {
    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;

    public Set<StudentDto> execute(int page, int size) {
        return studentRepository.findAllByStatus(Pageable.ofSize(size).withPage(page), true)
                .stream()
                .map(studentConverter::toDto)
                .collect(Collectors.toSet());
    }
}
