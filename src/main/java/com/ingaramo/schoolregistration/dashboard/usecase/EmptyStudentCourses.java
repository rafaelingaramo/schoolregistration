package com.ingaramo.schoolregistration.dashboard.usecase;

import com.ingaramo.schoolregistration.course.CourseConverter;
import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmptyStudentCourses {
    private final CourseRepository courseRepository;
    private final CourseConverter courseConverter;

    public Set<CourseDto> execute(int page, int pageSize) {
        return courseRepository.emptyStudentCourses(Pageable.ofSize(pageSize).withPage(page))
                .stream()
                .map(courseConverter::toDto)
                .collect(Collectors.toSet());
    }
}
