package com.ingaramo.schoolregistration.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {
    private Integer id;
    private String name;
}
