package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class SaveStudentTest {
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter converter = new StudentConverter(courseConverter);
    private final SaveStudent saveStudent = new SaveStudent(repository, converter);

    @Test
    public void givenInvalidBodyMustThrowException() {
        //given
        StudentDto dto = new StudentDto();
        //when
        InvalidStudentException invalidStudentException
                = Assertions.assertThrows(InvalidStudentException.class, () -> saveStudent.execute(dto));
        //then
        assert invalidStudentException != null;
        assert invalidStudentException.getMessage().contains("Invalid Student provided ");
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenValidBodyCheckSaveMethodCalled() {
        //given
        Student databaseStudent = Student.builder()
                .id(1)
                .name(UUID.randomUUID().toString())
                .build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(databaseStudent);

        StudentDto dto = StudentDto.builder()
                .name(UUID.randomUUID().toString())
                .build();

        //when
        StudentDto execute = saveStudent.execute(dto);
        //then
        assert execute != null;
        assert execute.getName().equals(databaseStudent.getName());
        assert execute.getId().equals(databaseStudent.getId());
        Mockito.verify(repository, Mockito.times(1)).save(converter.toNewEntity(dto));
    }
}
