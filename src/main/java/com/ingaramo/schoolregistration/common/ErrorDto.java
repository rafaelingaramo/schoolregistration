package com.ingaramo.schoolregistration.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private Integer status;
    private String message;

    public static ErrorDto of(RuntimeException ex, Integer status) {
        return ErrorDto.builder()
                .status(status)
                .message(ex.getMessage())
                .build();
    }
}
