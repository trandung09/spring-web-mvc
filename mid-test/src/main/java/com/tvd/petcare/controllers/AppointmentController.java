package com.tvd.petcare.controllers;

import com.tvd.petcare.dtos.requests.CreateAppointmentRequest;
import com.tvd.petcare.dtos.responses.ApiResponse;
import com.tvd.petcare.dtos.responses.AppointmentResponse;
import com.tvd.petcare.enums.AppointmentStatus;
import com.tvd.petcare.services.appointments.IAppointmentService;
import com.tvd.petcare.utils.BindingUtils;
import com.tvd.petcare.utils.PageableUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment-Controller")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentController {

    final IAppointmentService appointmentService;

    @GetMapping("/{appointmentId}")
    public ApiResponse<AppointmentResponse> getAppointmentDetails(@PathVariable Long appointmentId) throws Exception {

        return ApiResponse.<AppointmentResponse>builder()
                .message("Getting appointment details successfully")
                .data(appointmentService.getAppointmentDetails(appointmentId))
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("")
    public ApiResponse<Page<AppointmentResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "createdAt,desc") String sort)
            throws Exception {

        Pageable pageable = PageableUtils.convertPageable(page, size, sort);

        return ApiResponse.<Page<AppointmentResponse>>builder()
                .data(appointmentService.getAllAppointments(pageable))
                .message("Get all appointment successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("")
    public ApiResponse<?> createAppointment(@Valid @RequestBody CreateAppointmentRequest request,
                                            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            System.out.println("############################");
            System.out.println("Errors: " + bindingResult.getAllErrors());
            System.out.println("############################");

            return BindingUtils.handleBindingErrors(bindingResult);
        }

        System.out.println(request);

        return ApiResponse.builder()
                .message("Create appointment successfully")
                .status(HttpStatus.CREATED)
                .data(appointmentService.createAppointment(request))
                .build();
    }

    @PatchMapping("/{appointmentId}")
    public ApiResponse<AppointmentResponse> changeAppointmentStatus(@PathVariable Long appointmentId,
                                                                    @RequestParam String status)  throws Exception {

        AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());

        return ApiResponse.<AppointmentResponse>builder()
                .message("Update appointment status successfully")
                .status(HttpStatus.OK)
                .data(appointmentService.changAppointmentStatus(appointmentId, newStatus))
                .build();
    }
}
