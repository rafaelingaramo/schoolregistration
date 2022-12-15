package com.ingaramo.schoolregistration.config;

import com.ingaramo.schoolregistration.common.ErrorDto;
import com.ingaramo.schoolregistration.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value={BadRequestException.class})
    public ResponseEntity<ErrorDto> badRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.of(ex, HttpStatus.BAD_REQUEST.value()));
    }
}
