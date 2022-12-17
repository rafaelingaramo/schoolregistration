package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentException;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import com.ingaramo.schoolregistration.student.usecase.EditStudent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class EditStudentTest {
    private static final Integer NONEXISTENT_ID = 999;
    private static final Integer VALID_ID = 1;
    private static final Student VALID_SAVED_COURSE = Student.builder()
            .id(VALID_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter converter = new StudentConverter(courseConverter);
    private final EditStudent editStudent = new EditStudent(repository, converter);

    @BeforeEach
    public void setUp() {
        Mockito.when(repository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
        Mockito.when(repository.findById(VALID_ID)).thenReturn(Optional.ofNullable(VALID_SAVED_COURSE));
        Mockito.when(repository.save(Mockito.any())).thenReturn(VALID_SAVED_COURSE);
    }

    @Test
    public void givenValidPayloadValidIdValidateSavedEntityAndResult() {
        //given
        String toBePersistedName = UUID.randomUUID().toString();
        StudentDto studentDto = StudentDto.builder()
                .id(VALID_ID)
                .name(toBePersistedName)
                .build();
        //when
        StudentDto resultDto = editStudent.execute(studentDto);

        //then
        assert resultDto != null;
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(1)).save(converter.toEntity(studentDto));
    }

    @Test
    public void givenValidPayloadButNonExistentIdMustThrowException() {
        //given
        StudentDto studentDto = StudentDto.builder()
                .id(NONEXISTENT_ID)
                .name(UUID.randomUUID().toString())
                .build();
        //when
        InvalidStudentIdException invalidStudentException = Assertions
                .assertThrows(InvalidStudentIdException.class, () -> editStudent.execute(studentDto));

        //then
        assert invalidStudentException != null;
        assert invalidStudentException.getMessage().equals("Invalid id: " + NONEXISTENT_ID);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidPayloadMustThrowException() {
        //given
        StudentDto studentDto = StudentDto.builder()
                .build();
        //when
        InvalidStudentException invalidStudentException = Assertions
                .assertThrows(InvalidStudentException.class, () -> editStudent.execute(studentDto));

        //then
        assert invalidStudentException != null;
        assert invalidStudentException.getMessage().contains("Invalid Student provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidIdPayloadMustThrowException() {
        //given
        StudentDto studentDto = StudentDto.builder()
                .name(UUID.randomUUID().toString())
                .build();
        //when
        InvalidStudentException invalidStudentException = Assertions
                .assertThrows(InvalidStudentException.class, () -> editStudent.execute(studentDto));

        //then
        assert invalidStudentException != null;
        assert invalidStudentException.getMessage().contains("Invalid Student provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidNamePayloadMustThrowException() {
        //given
        StudentDto studentDto = StudentDto.builder()
                .id(1)
                .build();
        //when
        InvalidStudentException invalidStudentException = Assertions
                .assertThrows(InvalidStudentException.class, () -> editStudent.execute(studentDto));

        //then
        assert invalidStudentException != null;
        assert invalidStudentException.getMessage().contains("Invalid Student provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }
}
