package com.tvd.petcare.utils;

import com.tvd.petcare.dtos.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingUtils {

    public static ApiResponse<List<String>> handleBindingErrors(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ApiResponse.<List<String>>builder()
                .message("Validation errors occurred")
                .status(HttpStatus.BAD_REQUEST)
                .data(errorMessages)
                .build();
    }
}
