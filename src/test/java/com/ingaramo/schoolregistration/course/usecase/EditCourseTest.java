package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseException;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class EditCourseTest {
    private static final Integer NONEXISTENT_ID = 999;
    private static final Integer VALID_ID = 1;
    private static final Course VALID_SAVED_COURSE = Course.builder()
            .id(VALID_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final CourseRepository repository = Mockito.mock(CourseRepository.class);
    private final CourseConverter converter = new CourseConverter();
    private final EditCourse editCourse = new EditCourse(repository, converter);

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
        CourseDto courseDto = CourseDto.builder()
                .id(VALID_ID)
                .name(toBePersistedName)
                .build();
        //when
        CourseDto resultDto = editCourse.execute(courseDto);

        //then
        assert resultDto != null;
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(1)).save(converter.toEntity(courseDto));
    }

    @Test
    public void givenValidPayloadButNonExistentIdMustThrowException() {
        //given
        CourseDto courseDto = CourseDto.builder()
                .id(NONEXISTENT_ID)
                .name(UUID.randomUUID().toString())
                .build();
        //when
        InvalidCourseIdException invalidCourseException = Assertions
                .assertThrows(InvalidCourseIdException.class, () -> editCourse.execute(courseDto));

        //then
        assert invalidCourseException != null;
        assert invalidCourseException.getMessage().equals("Invalid id: " + NONEXISTENT_ID);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidPayloadMustThrowException() {
        //given
        CourseDto courseDto = CourseDto.builder()
                .build();
        //when
        InvalidCourseException invalidCourseException = Assertions
                .assertThrows(InvalidCourseException.class, () -> editCourse.execute(courseDto));

        //then
        assert invalidCourseException != null;
        assert invalidCourseException.getMessage().contains("Invalid Course provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidIdPayloadMustThrowException() {
        //given
        CourseDto courseDto = CourseDto.builder()
                .name(UUID.randomUUID().toString())
                .build();
        //when
        InvalidCourseException invalidCourseException = Assertions
                .assertThrows(InvalidCourseException.class, () -> editCourse.execute(courseDto));

        //then
        assert invalidCourseException != null;
        assert invalidCourseException.getMessage().contains("Invalid Course provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenInvalidNamePayloadMustThrowException() {
        //given
        CourseDto courseDto = CourseDto.builder()
                .id(1)
                .build();
        //when
        InvalidCourseException invalidCourseException = Assertions
                .assertThrows(InvalidCourseException.class, () -> editCourse.execute(courseDto));

        //then
        assert invalidCourseException != null;
        assert invalidCourseException.getMessage().contains("Invalid Course provided");
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }
}
