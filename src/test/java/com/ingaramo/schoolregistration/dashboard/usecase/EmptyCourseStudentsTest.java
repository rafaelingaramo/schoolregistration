package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.student.StudentConverter;
import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.StudentProjection;
import com.ingaramo.schoolregistration.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EmptyCourseStudentsTest {
    private static final Integer ID = 1;
    private static final String NAME = UUID.randomUUID().toString();

    private static final StudentProjection STUDENT_PROJECTION = new StudentProjection(){
        @Override
        public Integer getId() {
            return ID;
        }

        @Override
        public String getName() {
            return NAME;
        }
    };

    private final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter studentConverter = new StudentConverter(courseConverter);
    private final EmptyCourseStudents emptyCourseStudents = new EmptyCourseStudents(studentRepository, studentConverter);


    @Test
    public void givenEmptyDatabaseResultsShouldHaveEmptyDtoResults() {
        //given
        Mockito.when(studentRepository.emptyCourseStudents(Mockito.any())).thenReturn(Page.empty());
        //when
        Set<StudentDto> execute = emptyCourseStudents.execute(0, 10);
        //then
        assert execute != null;
        assert execute.isEmpty();
    }

    @Test
    public void givenNotEmptyDatabaseResultsShouldValidateDtoResults() {
        //given
        List<StudentProjection> objects = new ArrayList<>();
        objects.add(STUDENT_PROJECTION);

        Mockito.when(studentRepository.emptyCourseStudents(Mockito.any())).thenReturn(new PageImpl<>(objects));
        //when
        List<StudentDto> execute = new ArrayList<>(emptyCourseStudents.execute(0, 10));
        //then
        assert execute.get(0).getId().equals(ID);
        assert execute.get(0).getName().equals(NAME);
    }
}
