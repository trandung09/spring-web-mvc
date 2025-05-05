package com.tvd.petcare.mapper;

import com.tvd.petcare.dtos.requests.CreateAppointmentRequest;
import com.tvd.petcare.dtos.responses.AppointmentResponse;
import com.tvd.petcare.entities.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment toAppointment(CreateAppointmentRequest request);

    AppointmentResponse toAppointmentResponse(Appointment appointment);
}
