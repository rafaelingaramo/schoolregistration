package com.ingaramo.schoolregistration.course.exception;

import com.ingaramo.schoolregistration.exception.BadRequestException;

public class InvalidCourseIdException extends BadRequestException {
    public InvalidCourseIdException(String message) {
        super(message);
    }
}
