package com.ingaramo.schoolregistration.enroll.exception;

import com.ingaramo.schoolregistration.exception.BadRequestException;

public class MaximumStudentsAchievedByCourseException extends BadRequestException {
    private static final String MESSAGE = "Max students per course achieved, maximum: 50, course_id: ";
    public MaximumStudentsAchievedByCourseException(Integer courseId) {
        super(MESSAGE + courseId);
    }
}
