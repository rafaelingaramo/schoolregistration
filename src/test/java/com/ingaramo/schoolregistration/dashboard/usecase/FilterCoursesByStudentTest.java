package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentRepository;
import com.ingaramo.schoolregistration.student.exception.InvalidStudentIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FilterCoursesByStudentTest {
    private static final Integer STUDENT_EMPTY_COURSES = 1;
    private static final Integer STUDENT_VALID_COURSES = 2;
    private static final Integer INVALID_STUDENT = 999;
    private static final Student DB_STUDENT_EMPTY_COURSES = Student.builder()
            .id(STUDENT_EMPTY_COURSES)
            .name(UUID.randomUUID().toString())
            .studentCourses(Collections.emptySet())
        .build();
    private static final Set<Course> STUDENT_COURSES = new HashSet<>();
    private static final Student DB_STUDENT_VALID_COURSES = Student.builder()
            .id(STUDENT_EMPTY_COURSES)
            .name(UUID.randomUUID().toString())
            .studentCourses(STUDENT_COURSES)
            .build();
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final FilterCoursesByStudent filterCoursesByStudent = new FilterCoursesByStudent(repository, courseConverter);

    static {
        STUDENT_COURSES.add(Course.builder()
                        .id(1)
                        .name(UUID.randomUUID().toString())
                .build());
    }

    @BeforeEach
    public void setUp() {
        Mockito.when(repository.findByIdAndStatus(INVALID_STUDENT, true))
                .thenReturn(Optional.empty());
        Mockito.when(repository.findByIdAndStatus(STUDENT_EMPTY_COURSES, true))
                .thenReturn(Optional.ofNullable(DB_STUDENT_EMPTY_COURSES));
        Mockito.when(repository.findByIdAndStatus(STUDENT_VALID_COURSES, true))
                .thenReturn(Optional.ofNullable(DB_STUDENT_VALID_COURSES));
    }

    @Test
    public void givenInvalidIdMustThrowException() {
        //when
        InvalidStudentIdException invalidStudentIdException =
                Assertions.assertThrows(InvalidStudentIdException.class, () -> filterCoursesByStudent.execute(INVALID_STUDENT));

        //then
        assert invalidStudentIdException != null;
        assert invalidStudentIdException.getMessage().equals("Invalid id provided");
    }

    @Test
    public void givenValidIdButEmptyCourses() {
        //when
        Set<CourseDto> execute = filterCoursesByStudent.execute(STUDENT_EMPTY_COURSES);

        //then
        assert execute.isEmpty();
    }

    @Test
    public void givenValidIdAndValidCourses() {
        //when
        Set<CourseDto> execute = filterCoursesByStudent.execute(STUDENT_VALID_COURSES);

        //then
        assert execute.size() == 1;
        assert new ArrayList<>(execute).get(0).getId().equals(1);
    }
}
