package com.ingaramo.schoolregistration.enroll.usecase;

import com.ingaramo.schoolregistration.common.Pair;
import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.exception.BadRequestException;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.UUID;

public class EnrollStudentIntoCourseTest {
    private static final Integer VALID_STUDENT = 1;
    private static final Integer VALID_COURSE = 2;
    private static final Course VALID_SAVED_COURSE = Course.builder()
            .id(VALID_COURSE)
            .name(UUID.randomUUID().toString())
            .students(new HashSet<>())
            .build();
    private static final Student VALID_SAVED_STUDENT = Student.builder()
            .id(VALID_STUDENT)
            .name(UUID.randomUUID().toString())
            .studentCourses(new HashSet<>())
            .build();

    private final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter studentConverter = new StudentConverter(courseConverter);
    private final ValidateCourseEnroll validateCourseEnroll = Mockito.mock(ValidateCourseEnroll.class);
    private final EnrollStudentIntoCourse enrollStudentIntoCourse = new EnrollStudentIntoCourse(studentRepository, studentConverter,
            validateCourseEnroll);

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void givenAnyInvalidRuleMustThrowBadRequestException() {
        //given
        Mockito.when(validateCourseEnroll.execute(VALID_COURSE, VALID_STUDENT)).thenThrow(new BadRequestException("any"));
        //when
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class,
                () -> enrollStudentIntoCourse.execute(VALID_COURSE, VALID_STUDENT));
        //then
        assert badRequestException != null;
        assert badRequestException.getMessage().equals("any");
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenValidRulesMustCheckSaveParametersAndResponse() {
        //given
        Pair<Student, Course> studentCoursePair = new Pair<>(VALID_SAVED_STUDENT, VALID_SAVED_COURSE);
        Mockito.when(validateCourseEnroll.execute(VALID_COURSE, VALID_STUDENT)).thenReturn(studentCoursePair);
        Mockito.when(studentRepository.save(Mockito.any())).thenReturn(VALID_SAVED_STUDENT);

        //when
        StudentDto execute = enrollStudentIntoCourse.execute(VALID_COURSE, VALID_STUDENT);
        VALID_SAVED_STUDENT.getStudentCourses().add(VALID_SAVED_COURSE);
        VALID_SAVED_COURSE.getStudents().add(VALID_SAVED_STUDENT);
        //then
        Mockito.verify(studentRepository, Mockito.times(1)).save(VALID_SAVED_STUDENT);
        assert execute != null;
    }
}
