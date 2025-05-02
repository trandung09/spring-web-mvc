package com.tvd.petcare.controllers;

import com.tvd.petcare.services.appointments.IAppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment-Controller")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentController {

    final IAppointmentService appointmentService;
}
