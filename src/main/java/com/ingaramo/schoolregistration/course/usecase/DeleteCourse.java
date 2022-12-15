package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseRepository;
import com.ingaramo.schoolregistration.course.exception.InvalidCourseIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteCourse {
    private final CourseRepository repository;

    public void execute(Integer id) {
        Optional<Course> optionalCourse = repository.findById(id);
        Course course = optionalCourse
                .orElseThrow(() -> new InvalidCourseIdException("Invalid id given to deletion: " + id));
        course.setStatus(false);
        repository.save(course);
    }
}
