package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteStudent {
    private final StudentRepository repository;

    public void execute(Integer id) {
        Optional<Student> optionalStudent = repository.findById(id);
        Student student = optionalStudent
                .orElseThrow(() -> new InvalidStudentIdException("Invalid id given to deletion: " + id));
        student.setStatus(false);
        repository.save(student);
    }
}
