package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCourse {
    private final CourseRepository repository;
    private final CourseConverter converter;

    public CourseDto execute(Integer id) {
        return repository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new InvalidCourseIdException("Invalid id provided: " + id));
    }
}
