package com.ingaramo.schoolregistration.enroll.entrypoint;

import com.ingaramo.schoolregistration.enroll.usecase.EnrollStudentIntoCourse;
import com.ingaramo.schoolregistration.student.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseEnrollController {
    private final EnrollStudentIntoCourse enrollStudentIntoCourse;

    @PostMapping("/{courseId}/enroll/student/{studentId}")
    public StudentDto enrollToCourse(@PathVariable("courseId") Integer courseId,
                                     @PathVariable("studentId") Integer studentId) {
        return enrollStudentIntoCourse.execute(courseId, studentId);
    }
}
