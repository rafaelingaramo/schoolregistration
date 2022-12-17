package com.ingaramo.schoolregistration.enroll.exception;

import com.ingaramo.schoolregistration.exception.BadRequestException;

public class MaximumCoursesByStudentAchievedException extends BadRequestException {

    private static final String MESSAGE = "Max courses per student achieved, maximum: 5, student_id: ";
    public MaximumCoursesByStudentAchievedException(Integer studentId) {
        super(MESSAGE + studentId);
    }
}
