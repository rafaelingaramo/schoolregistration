package com.ingaramo.schoolregistration.student.usecase;


import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class DeleteStudentTest {
    private static final Integer NONEXISTENT_ID = 999;
    private static final Integer STUDENT_ID = 1;
    private static final Student VALID_SAVED_STUDENT = Student.builder()
            .id(STUDENT_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    private final DeleteStudent deleteStudent = new DeleteStudent(studentRepository);

    @BeforeEach
    public void setUp() {
        Mockito.when(studentRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(VALID_SAVED_STUDENT));
        Mockito.when(studentRepository.save(Mockito.any())).thenReturn(VALID_SAVED_STUDENT);
    }

    @Test
    public void givenWrongIdTryDeleteStudentAndGetException() {
        //when
        InvalidStudentIdException thrown = Assertions.assertThrows(InvalidStudentIdException.class, () -> {
            deleteStudent.execute(NONEXISTENT_ID);
        });

        //then
        Mockito.verify(studentRepository, Mockito.times(1)).findById(NONEXISTENT_ID);
        assert thrown != null;
        assert thrown.getMessage().equals("Invalid id given to deletion: " + NONEXISTENT_ID);
    }

    @Test
    public void givenValidIdTryDeleteStudentAndSoftDeletion() {
        //when
        deleteStudent.execute(STUDENT_ID);
        Student validSavedStudent = VALID_SAVED_STUDENT;
        validSavedStudent.setStatus(false);
        //then
        Mockito.verify(studentRepository, Mockito.times(1)).findById(STUDENT_ID);
        Mockito.verify(studentRepository, Mockito.times(1)).save(validSavedStudent);
    }
}
