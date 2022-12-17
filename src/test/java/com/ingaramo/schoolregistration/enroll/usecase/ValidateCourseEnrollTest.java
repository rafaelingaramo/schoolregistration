package com.ingaramo.schoolregistration.enroll.usecase;

import com.ingaramo.schoolregistration.common.Pair;
import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import com.ingaramo.schoolregistration.enroll.exception.MaximumCoursesByStudentAchievedException;
import com.ingaramo.schoolregistration.enroll.exception.MaximumStudentsAchievedByCourseException;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class ValidateCourseEnrollTest {
    private static class MockedValidateCourseEnroll extends ValidateCourseEnroll {
        public MockedValidateCourseEnroll(CourseRepository courseRepository, StudentRepository studentRepository) {
            super(courseRepository, studentRepository);
            this.maximumCoursesPerStudent = 5;
            this.maximumStudentsPerCourse = 50;
        }
    }
    private final CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    private final ValidateCourseEnroll validateCourseEnroll = new MockedValidateCourseEnroll(courseRepository, studentRepository);

    private static final Integer INVALID_STUDENT_ID = 999;
    private static final Integer INVALID_COURSE_ID = 999;
    private static final Integer VALID_STUDENT_ID = 1;
    private static final Integer VALID_COURSE_ID = 1;

    private static final Student VALID_STUDENT = Student.builder()
            .id(VALID_STUDENT_ID)
            .name(UUID.randomUUID().toString())
            .build();

    private static final Course VALID_COURSE = Course.builder()
            .id(VALID_COURSE_ID)
            .name(UUID.randomUUID().toString())
            .build();


    @BeforeEach
    public void setUp() {
        Mockito.when(courseRepository.findByIdAndStatus(INVALID_COURSE_ID, true))
                .thenReturn(Optional.empty());
        Mockito.when(studentRepository.findByIdAndStatus(INVALID_STUDENT_ID, true))
                .thenReturn(Optional.empty());

        Mockito.when(courseRepository.findByIdAndStatus(VALID_COURSE_ID, true))
                .thenReturn(Optional.of(VALID_COURSE));
        Mockito.when(studentRepository.findByIdAndStatus(VALID_STUDENT_ID, true))
                .thenReturn(Optional.of(VALID_STUDENT));
    }

    @Test
    public void givenInvalidStudentIdMustThrowException() {
        //when
        InvalidCourseIdException invalidCourseIdException =
                Assertions.assertThrows(InvalidCourseIdException.class, () -> validateCourseEnroll.execute(INVALID_COURSE_ID, INVALID_STUDENT_ID));
        //then
        Mockito.verify(courseRepository, Mockito.times(0)).countStudentsByCourse(Mockito.anyInt());
        Mockito.verify(studentRepository, Mockito.times(0)).findByIdAndStatus(Mockito.anyInt(), Mockito.anyBoolean());
        Mockito.verify(studentRepository, Mockito.times(0)).countCoursesByStudent(Mockito.anyInt());
        assert invalidCourseIdException != null;
        assert invalidCourseIdException.getMessage().equals("Invalid course id: " + INVALID_COURSE_ID);
    }

    @Test
    public void givenValidCourseIdButMaximumCoursesPerStudentMustThrowException() {
        //given
        Mockito.when(courseRepository.countStudentsByCourse(VALID_COURSE_ID)).thenReturn(validateCourseEnroll.maximumStudentsPerCourse);
        //when
        MaximumStudentsAchievedByCourseException maximumStudentsAchievedByCourseException =
                Assertions.assertThrows(MaximumStudentsAchievedByCourseException.class, () -> validateCourseEnroll.execute(VALID_COURSE_ID, VALID_STUDENT_ID));
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).countStudentsByCourse(VALID_COURSE_ID);
        Mockito.verify(studentRepository, Mockito.times(0)).findByIdAndStatus(Mockito.anyInt(), Mockito.anyBoolean());
        Mockito.verify(studentRepository, Mockito.times(0)).countCoursesByStudent(Mockito.anyInt());
        assert maximumStudentsAchievedByCourseException != null;
        assert maximumStudentsAchievedByCourseException.getMessage().equals("Max students per course achieved, maximum: 50, course_id: " + VALID_COURSE_ID);
    }

    @Test
    public void givenValidCourseIdButInvalidStudentMustThrowException() {
        //given
        Mockito.when(courseRepository.countStudentsByCourse(VALID_COURSE_ID)).thenReturn(0);
        //when
        InvalidStudentIdException invalidStudentIdException =
                Assertions.assertThrows(InvalidStudentIdException.class, () -> validateCourseEnroll.execute(VALID_COURSE_ID, INVALID_STUDENT_ID));
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).countStudentsByCourse(VALID_COURSE_ID);
        Mockito.verify(studentRepository, Mockito.times(1)).findByIdAndStatus(INVALID_STUDENT_ID, true);
        Mockito.verify(studentRepository, Mockito.times(0)).countCoursesByStudent(Mockito.anyInt());
        assert invalidStudentIdException != null;
        assert invalidStudentIdException.getMessage().equals("Invalid student id: " + INVALID_STUDENT_ID);
    }

    @Test
    public void givenValidCourseIdValidStudentButMaxCoursesPerStudentMustThrowException() {
        //given
        Mockito.when(courseRepository.countStudentsByCourse(VALID_COURSE_ID)).thenReturn(0);
        Mockito.when(studentRepository.countCoursesByStudent(VALID_STUDENT_ID)).thenReturn(5);
        //when
        MaximumCoursesByStudentAchievedException maximumCoursesByStudentAchievedException =
                Assertions.assertThrows(MaximumCoursesByStudentAchievedException.class, () -> validateCourseEnroll.execute(VALID_COURSE_ID, VALID_STUDENT_ID));
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).countStudentsByCourse(VALID_COURSE_ID);
        Mockito.verify(studentRepository, Mockito.times(1)).findByIdAndStatus(VALID_STUDENT_ID, true);
        Mockito.verify(studentRepository, Mockito.times(1)).countCoursesByStudent(VALID_STUDENT_ID);
        assert maximumCoursesByStudentAchievedException != null;
        assert maximumCoursesByStudentAchievedException.getMessage().equals("Max courses per student achieved, student_id: " + VALID_STUDENT_ID);
    }

    @Test
    public void givenValidCourseIdValidStudentCheckValidResult() {
        //given
        Mockito.when(courseRepository.countStudentsByCourse(VALID_COURSE_ID)).thenReturn(0);
        Mockito.when(studentRepository.countCoursesByStudent(VALID_STUDENT_ID)).thenReturn(0);
        //when
        Pair<Student, Course> execute = validateCourseEnroll.execute(VALID_COURSE_ID, VALID_STUDENT_ID);
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).countStudentsByCourse(VALID_COURSE_ID);
        Mockito.verify(studentRepository, Mockito.times(1)).findByIdAndStatus(VALID_STUDENT_ID, true);
        Mockito.verify(studentRepository, Mockito.times(1)).countCoursesByStudent(VALID_STUDENT_ID);
        assert execute.getFirst() == VALID_STUDENT;
        assert execute.getSecond() == VALID_COURSE;
    }
}
