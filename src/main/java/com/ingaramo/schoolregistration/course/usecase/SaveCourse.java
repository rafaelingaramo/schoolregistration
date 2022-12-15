package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCourse {
    private final CourseRepository repository;
    private final CourseConverter converter;

    public CourseDto execute(@NonNull CourseDto course) {
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new InvalidCourseException(course);
        }

        return converter.toDto(repository.save(converter.toNewEntity(course)));
    }
}
