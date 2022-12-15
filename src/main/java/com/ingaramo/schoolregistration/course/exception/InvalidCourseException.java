package com.ingaramo.schoolregistration.course.exception;

import com.ingaramo.schoolregistration.course.CourseDto;
import com.ingaramo.schoolregistration.exception.BadRequestException;
import lombok.NonNull;

public class InvalidCourseException extends BadRequestException {

    public InvalidCourseException(@NonNull CourseDto course) {
        super("Invalid Course provided " + course);
    }
}
