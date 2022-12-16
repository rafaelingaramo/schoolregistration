package com.ingaramo.schoolregistration.student.exception;

import com.ingaramo.schoolregistration.exception.BadRequestException;
import com.ingaramo.schoolregistration.student.StudentDto;
import lombok.NonNull;

public class InvalidStudentException extends BadRequestException {

    public InvalidStudentException(@NonNull StudentDto student) {
        super("Invalid Student provided " + student);
    }
}
