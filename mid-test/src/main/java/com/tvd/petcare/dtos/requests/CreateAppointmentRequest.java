package com.tvd.petcare.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {

    @NotBlank(message = "Name cannot blank")
    @NotEmpty
    String bookerName;

    @NotBlank(message = "Phone number cannot blank")
    @NotEmpty
    String bookerPhoneNumber;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot blank")
    @NotEmpty
    String bookerEmail;

    @NotBlank(message = "Content cannot blank")
    @NotEmpty
    String content;
}
