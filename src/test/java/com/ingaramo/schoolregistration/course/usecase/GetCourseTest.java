package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class GetCourseTest {
    private final Integer NONEXISTENT_ID = 999;
    private final Integer EXISTENT_ID = 1;
    private final Course VALID_COURSE = Course.builder()
            .id(EXISTENT_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final CourseRepository repository = Mockito.mock(CourseRepository.class);
    private final CourseConverter converter = new CourseConverter();

    private final GetCourse getCourse = new GetCourse(repository, converter);

    @BeforeEach
    public void setUp() {
        Mockito.when(repository.findByIdAndStatus(NONEXISTENT_ID, true)).thenReturn(Optional.empty());
        Mockito.when(repository.findByIdAndStatus(EXISTENT_ID, true)).thenReturn(Optional.ofNullable(VALID_COURSE));
    }

    @Test
    public void givenInvalidIdShouldThrowException() {
        //when
        InvalidCourseIdException invalidCourseIdException = Assertions
                .assertThrows(InvalidCourseIdException.class, () -> getCourse.execute(NONEXISTENT_ID));

        //then
        assert invalidCourseIdException != null;
        assert invalidCourseIdException.getMessage().equals("Invalid id provided: " + NONEXISTENT_ID);
        Mockito.verify(repository, Mockito.times(1)).findByIdAndStatus(NONEXISTENT_ID, true);
    }

    @Test
    public void givenValidIdValidateResult() {
        //when
        CourseDto execute = getCourse.execute(EXISTENT_ID);

        //then
        assert execute != null;
        assert execute.getId().equals(VALID_COURSE.getId());
        assert execute.getName().equals(VALID_COURSE.getName());
    }
}
