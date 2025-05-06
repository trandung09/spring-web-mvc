package com.tvd.petcare.exceptions;

import com.tvd.petcare.dtos.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ApiResponse<?> handleAppException(final AppException e) {

        return ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .isError(true)
                .build();
    }
}
