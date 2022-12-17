package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class SaveCourseTest {
    private final CourseRepository repository = Mockito.mock(CourseRepository.class);
    private final CourseConverter converter = new CourseConverter();
    private final SaveCourse saveCourse = new SaveCourse(repository, converter);

    @Test
    public void givenInvalidBodyMustThrowException() {
        //given
        CourseDto dto = new CourseDto();
        //when
        InvalidCourseException invalidCourseException
                = Assertions.assertThrows(InvalidCourseException.class, () -> saveCourse.execute(dto));
        //then
        assert invalidCourseException != null;
        assert invalidCourseException.getMessage().contains("Invalid Course provided ");
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void givenValidBodyCheckSaveMethodCalled() {
        //given
        Course databaseCourse = Course.builder()
                .id(1)
                .name(UUID.randomUUID().toString())
                .build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(databaseCourse);

        CourseDto dto = CourseDto.builder()
                .name(UUID.randomUUID().toString())
                .build();

        //when
        CourseDto execute = saveCourse.execute(dto);
        //then
        assert execute != null;
        assert execute.getName().equals(databaseCourse.getName());
        assert execute.getId().equals(databaseCourse.getId());
        Mockito.verify(repository, Mockito.times(1)).save(converter.toNewEntity(dto));
    }
}
