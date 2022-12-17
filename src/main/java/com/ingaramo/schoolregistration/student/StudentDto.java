package com.ingaramo.schoolregistration.student;

import com.ingaramo.schoolregistration.course.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Integer id;
    private String name;
    private Set<CourseDto> courses;
}
