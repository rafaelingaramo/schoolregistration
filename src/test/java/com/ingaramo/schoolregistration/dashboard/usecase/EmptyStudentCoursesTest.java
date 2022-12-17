package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseProjection;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseProjection;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EmptyStudentCoursesTest {
    private static final Integer ID = 1;
    private static final String NAME = UUID.randomUUID().toString();

    private static final CourseProjection COURSE_PROJECTION = new CourseProjection(){
        @Override
        public Integer getId() {
            return ID;
        }

        @Override
        public String getName() {
            return NAME;
        }
    };

    private final CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final EmptyStudentCourses emptyStudentCourses = new EmptyStudentCourses(courseRepository, courseConverter);

    @Test
    public void givenEmptyDatabaseResultsShouldHaveEmptyDtoResults() {
        //given
        Mockito.when(courseRepository.emptyStudentCourses(Mockito.any())).thenReturn(Page.empty());
        //when
        Set<CourseDto> execute = emptyStudentCourses.execute(0, 10);
        //then
        assert execute != null;
        assert execute.isEmpty();
    }

    @Test
    public void givenNotEmptyDatabaseResultsShouldValidateDtoResults() {
        //given
        List<CourseProjection> objects = new ArrayList<>();
        objects.add(COURSE_PROJECTION);

        Mockito.when(courseRepository.emptyStudentCourses(Mockito.any())).thenReturn(new PageImpl<>(objects));
        //when
        List<CourseDto> execute = new ArrayList<>(emptyStudentCourses.execute(0, 10));
        //then
        assert execute.get(0).getId().equals(ID);
        assert execute.get(0).getName().equals(NAME);
    }
}
