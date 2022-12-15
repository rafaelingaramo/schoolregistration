package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseException;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditCourse {
    private final CourseRepository repository;
    private final CourseConverter converter;

    public CourseDto execute(CourseDto courseDto) {
        if (courseDto.getName() == null || courseDto.getName().isEmpty()) {
            throw new InvalidCourseException(courseDto);
        }
        if (courseDto.getId() == null) {
            throw new InvalidCourseException(courseDto);
        }

        Optional<Course> optionalCourse = repository.findById(courseDto.getId());
        Course course = optionalCourse
                .orElseThrow(() -> new InvalidCourseIdException("Invalid id: " + courseDto.getId()));
        course.setName(courseDto.getName());
        return converter.toDto(repository.save(course));
    }
}
