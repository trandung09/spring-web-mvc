package com.tvd.petcare.dtos.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    String message;
    HttpStatus status;
    T data;
    boolean isError = false;
}
