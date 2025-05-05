package com.tvd.petcare.services.appointments;

import com.tvd.petcare.dtos.requests.CreateAppointmentRequest;
import com.tvd.petcare.dtos.responses.AppointmentResponse;
import com.tvd.petcare.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAppointmentService {

    Page<AppointmentResponse> getAllAppointments(Pageable pageable) throws Exception;

    AppointmentResponse getAppointmentDetails(Long id) throws Exception;

    AppointmentResponse createAppointment(CreateAppointmentRequest appointment) throws Exception;

    AppointmentResponse changAppointmentStatus(Long id, AppointmentStatus appointmentStatus) throws Exception;
}
