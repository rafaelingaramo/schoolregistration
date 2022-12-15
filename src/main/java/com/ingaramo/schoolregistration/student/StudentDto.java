package com.ingaramo.schoolregistration.student;

import com.ingaramo.schoolregistration.course.CourseDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class StudentDto {
    private Integer id;
    private String name;
    private Set<CourseDto> courses;
}
