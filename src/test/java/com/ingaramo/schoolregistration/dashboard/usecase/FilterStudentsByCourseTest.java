package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.student.Student;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FilterStudentsByCourseTest {
    private final static Integer INVALID_COURSE = 999;
    private final static Integer VALID_COURSE = 1;
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter studentConverter = new StudentConverter(courseConverter);
    private final FilterStudentsByCourse filterStudentsByCourse = new FilterStudentsByCourse(repository, studentConverter);

    @BeforeEach
    public void setUp() {
        List<Student> validStudentSet = new ArrayList<>();
        validStudentSet.add(Student.builder()
                        .id(1)
                        .name(UUID.randomUUID().toString())
                .build());
        Mockito.when(repository.findAllByStatusAndCourse(Mockito.anyBoolean(), Mockito.eq(INVALID_COURSE), Mockito.any()))
                .thenReturn(Page.empty());
        Mockito.when(repository.findAllByStatusAndCourse(Mockito.anyBoolean(), Mockito.eq(VALID_COURSE), Mockito.any()))
                .thenReturn(new PageImpl<>(validStudentSet));
    }

    @Test
    public void givenValidIdButEmptyCourses() {
        //when
        Set<StudentDto> execute = filterStudentsByCourse.execute(INVALID_COURSE, 0, 10);

        //then
        assert execute.isEmpty();
    }

    @Test
    public void givenValidIdAndValidCourses() {
        //when
        Set<StudentDto> execute = filterStudentsByCourse.execute(VALID_COURSE, 0, 10);

        //then
        assert execute.size() == 1;
        assert new ArrayList<>(execute).get(0).getId().equals(1);
    }
}
