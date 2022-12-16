package com.ingaramo.schoolregistration.student.exception;

import com.ingaramo.schoolregistration.exception.BadRequestException;

public class InvalidStudentIdException extends BadRequestException {
    public InvalidStudentIdException(String message) {
        super(message);
    }
}
