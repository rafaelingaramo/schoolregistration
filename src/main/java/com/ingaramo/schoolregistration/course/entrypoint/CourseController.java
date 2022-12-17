package com.ingaramo.schoolregistration.course.entrypoint;

import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.course.usecase.DeleteCourse;
import com.ingaramo.schoolregistration.course.usecase.EditCourse;
import com.ingaramo.schoolregistration.course.usecase.FindAllCourses;
import com.ingaramo.schoolregistration.course.usecase.GetCourse;
import com.ingaramo.schoolregistration.course.usecase.SaveCourse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final FindAllCourses findAllCourses;
    private final SaveCourse saveCourse;
    private final EditCourse editCourse;
    private final GetCourse getCourse;
    private final DeleteCourse deleteCourse;

    @GetMapping
    public Set<CourseDto> getCourseList(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return findAllCourses.execute(page, size);
    }

    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCourse.execute(courseDto));
    }

    @PutMapping
    public ResponseEntity<CourseDto> editCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(editCourse.execute(courseDto));
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable("id") Integer courseId) {
        return getCourse.execute(courseId);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Integer courseId) {
        deleteCourse.execute(courseId);
    }
}
