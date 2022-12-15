package com.ingaramo.schoolregistration.course.usecase;

import com.ingaramo.schoolregistration.course.Course;
import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllCourses {
    private final CourseRepository courseRepository;
    private final CourseConverter courseConverter;

    public Set<CourseDto> execute(int page, int size) {
        return courseRepository.findAllByStatus(Pageable.ofSize(size).withPage(page), true)
                .stream()
                .map(courseConverter::toDto)
                .collect(Collectors.toSet());
    }
}
