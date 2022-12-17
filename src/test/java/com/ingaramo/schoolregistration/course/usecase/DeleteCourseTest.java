package com.ingaramo.schoolregistration.course.usecase;


import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class DeleteCourseTest {
    private static final Integer NONEXISTENT_ID = 999;
    private static final Integer COURSE_ID = 1;
    private static final Course VALID_SAVED_COURSE = Course.builder()
            .id(COURSE_ID)
            .name(UUID.randomUUID().toString())
            .build();
    private final CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private final DeleteCourse deleteCourse = new DeleteCourse(courseRepository);

    @BeforeEach
    public void setUp() {
        Mockito.when(courseRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
        Mockito.when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.ofNullable(VALID_SAVED_COURSE));
        Mockito.when(courseRepository.save(Mockito.any())).thenReturn(VALID_SAVED_COURSE);
    }

    @Test
    public void givenWrongIdTryDeleteCourseAndGetException() {
        //when
        InvalidCourseIdException thrown = Assertions.assertThrows(InvalidCourseIdException.class, () -> {
            deleteCourse.execute(NONEXISTENT_ID);
        });

        //then
        Mockito.verify(courseRepository, Mockito.times(1)).findById(NONEXISTENT_ID);
        assert thrown != null;
        assert thrown.getMessage().equals("Invalid id given to deletion: " + NONEXISTENT_ID);
    }

    @Test
    public void givenValidIdTryDeleteCourseAndSoftDeletion() {
        //when
        deleteCourse.execute(COURSE_ID);
        Course validSavedCourse = VALID_SAVED_COURSE;
        validSavedCourse.setStatus(false);
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).findById(COURSE_ID);
        Mockito.verify(courseRepository, Mockito.times(1)).save(validSavedCourse);
    }
}
