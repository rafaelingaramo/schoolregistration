package com.ingaramo.schoolregistration.student.usecase;

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

public class FindAllStudentsTest {
    private final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    private final CourseConverter courseConverter = new CourseConverter();
    private final StudentConverter studentConverter = new StudentConverter(courseConverter);
    private final FindAllStudents findAllStudents = new FindAllStudents(studentRepository, studentConverter);

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void givenEmptyDataInDatabaseReturnEmptyResults() {
        //given
        Mockito.when(studentRepository.findAllByStatus(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(Page.empty());

        //when
        Set<StudentDto> execute = findAllStudents.execute(0, 10);

        //then
        assert execute.isEmpty();
    }

    @Test
    public void givenExistingDataInDatabaseReturnDtoResults() {
        //given
        Integer id = 1;
        String name = UUID.randomUUID().toString();
        List<Student> databaseStudentList = new ArrayList<>();
        databaseStudentList.add(Student.builder()
                        .id(id)
                        .name(name)
                .build());

        Mockito.when(studentRepository.findAllByStatus(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(new PageImpl<>(databaseStudentList));

        //when
        Set<StudentDto> execute = findAllStudents.execute(0, 10);
        StudentDto studentDto = new ArrayList<>(execute).get(0);

        //then
        assert !execute.isEmpty();
        assert studentDto.getId().equals(id);
        assert studentDto.getName().equals(name);
    }
}
