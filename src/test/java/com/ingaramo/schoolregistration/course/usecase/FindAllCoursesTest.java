package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FindAllCoursesTest {
    private final CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final FindAllCourses findAllCourses = new FindAllCourses(courseRepository, courseConverter);

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void givenEmptyDataInDatabaseReturnEmptyResults() {
        //given
        Mockito.when(courseRepository.findAllByStatus(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(Page.empty());

        //when
        Set<CourseDto> execute = findAllCourses.execute(0, 10);

        //then
        assert execute.isEmpty();
    }

    @Test
    public void givenExistingDataInDatabaseReturnDtoResults() {
        //given
        Integer id = 1;
        String name = UUID.randomUUID().toString();
        List<Course> databaseCourseList = new ArrayList<>();
        databaseCourseList.add(Course.builder()
                        .id(id)
                        .name(name)
                .build());

        Mockito.when(courseRepository.findAllByStatus(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(new PageImpl<>(databaseCourseList));

        //when
        Set<CourseDto> execute = findAllCourses.execute(0, 10);
        CourseDto courseDto = new ArrayList<>(execute).get(0);

        //then
        assert !execute.isEmpty();
        assert courseDto.getId().equals(id);
        assert courseDto.getName().equals(name);
    }
}
