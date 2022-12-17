package com.ingaramo.schoolregistration.dashboard.entrypoint;

import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.dashboard.usecase.EmptyCourseStudents;
import com.ingaramo.schoolregistration.dashboard.usecase.EmptyStudentCourses;
import com.ingaramo.schoolregistration.dashboard.usecase.FilterCoursesByStudent;
import com.ingaramo.schoolregistration.dashboard.usecase.FilterStudentsByCourse;
import com.ingaramo.schoolregistration.student.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final FilterStudentsByCourse filterStudentsByCourse;
    private final FilterCoursesByStudent filterCoursesByStudent;
    private final EmptyStudentCourses emptyStudentCourses;
    private final EmptyCourseStudents emptyCourseStudents;

    @GetMapping(value = "/students-per-course/{course-id}")
    public Set<StudentDto> filterStudentsByCourse(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @PathVariable("course-id") Integer course) {
        return filterStudentsByCourse.execute(course, page, size);
    }

    @GetMapping(value = "/courses-per-student/{student-id}")
    public Set<CourseDto> filterCoursesPerStudent(@PathVariable("student-id") Integer course) {
        return filterCoursesByStudent.execute(course);
    }

    @GetMapping(value = "/empty-student-courses")
    public Set<CourseDto> emptyStudentCourses(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return emptyStudentCourses.execute(page, size);
    }

    @GetMapping(value = "/empty-course-students")
    public Set<StudentDto> emptyCourseStudents(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return emptyCourseStudents.execute(page, size);
    }
}
