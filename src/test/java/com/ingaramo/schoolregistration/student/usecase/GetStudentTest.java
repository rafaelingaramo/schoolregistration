package com.ingaramo.schoolregistration.student.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class GetStudentTest {
    private final Integer NONEXISTENT_ID = 999;
    private final Integer EXISTENT_ID = 1;
    private final Student VALID_COURSE = Student.builder()
            .id(EXISTENT_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter converter = new StudentConverter(courseConverter);

    private final GetStudent getStudent = new GetStudent(repository, converter);

    @BeforeEach
    public void setUp() {
        Mockito.when(repository.findByIdAndStatus(NONEXISTENT_ID, true)).thenReturn(Optional.empty());
        Mockito.when(repository.findByIdAndStatus(EXISTENT_ID, true)).thenReturn(Optional.ofNullable(VALID_COURSE));
    }

    @Test
    public void givenInvalidIdShouldThrowException() {
        //when
        InvalidStudentIdException invalidStudentIdException = Assertions
                .assertThrows(InvalidStudentIdException.class, () -> getStudent.execute(NONEXISTENT_ID));

        //then
        assert invalidStudentIdException != null;
        assert invalidStudentIdException.getMessage().equals("Invalid id provided: " + NONEXISTENT_ID);
        Mockito.verify(repository, Mockito.times(1)).findByIdAndStatus(NONEXISTENT_ID, true);
    }

    @Test
    public void givenValidIdValidateResult() {
        //when
        StudentDto execute = getStudent.execute(EXISTENT_ID);

        //then
        assert execute != null;
        assert execute.getId().equals(VALID_COURSE.getId());
        assert execute.getName().equals(VALID_COURSE.getName());
    }
}
