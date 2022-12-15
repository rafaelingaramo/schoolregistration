package com.ingaramo.schoolregistration.course;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CourseConverter {

    public CourseDto toDto(@NonNull Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

    public Course toNewEntity(CourseDto courseDto) {
        return Course.builder()
                .name(courseDto.getName())
                .status(true)
                .build();
    }

    public Course toEntity(CourseDto courseDto) {
        return Course.builder()
                .id(courseDto.getId())
                .name(courseDto.getName())
                .build();
    }
}
