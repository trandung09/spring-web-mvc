package com.tvd.petcare.dtos.responses;

import com.tvd.petcare.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long id;
    String bookerName;
    String bookerPhoneNumber;
    String bookerEmail;
    String content;
    AppointmentStatus status;
}
