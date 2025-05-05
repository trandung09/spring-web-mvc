package com.tvd.petcare.services.appointments;

import com.tvd.petcare.dtos.requests.CreateAppointmentRequest;
import com.tvd.petcare.dtos.responses.AppointmentResponse;
import com.tvd.petcare.entities.Appointment;
import com.tvd.petcare.enums.AppointmentStatus;
import com.tvd.petcare.exceptions.AppException;
import com.tvd.petcare.mapper.AppointmentMapper;
import com.tvd.petcare.repositories.AppointmentRepository;
import com.tvd.petcare.services.mails.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    final AppointmentMapper appointmentMapper;
    final AppointmentRepository appointmentRepository;
    final EmailService emailService;

    @Override
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable)
                .map(
                       appointmentMapper::toAppointmentResponse
                );
    }

    @Override
    public AppointmentResponse getAppointmentDetails(Long id) throws Exception {
        if (id == null) {
            throw new AppException("Get appointment details: appointment id cannot be null");
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException("Get appointment details: appointment not found by id"));

        return appointmentMapper.toAppointmentResponse(appointment);
    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        if (request == null) {
            throw new AppException("Create appointment: request cannot be null");
        }

        Appointment newAppointment = appointmentMapper.toAppointment(request);
        newAppointment.setStatus(AppointmentStatus.PENDING);

        newAppointment = appointmentRepository.save(newAppointment);

        return appointmentMapper.toAppointmentResponse(newAppointment);
    }

    @Override
    public AppointmentResponse changAppointmentStatus(Long id, AppointmentStatus appointmentStatus) throws Exception {
        if (id == null) {
            throw new AppException("Chang appointment: id cannot be null");
        }

        if (appointmentStatus == null) {
            throw new AppException("Chang appointment status: appointment status cannot be null");
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException("Change appointment status: appointment not found by id"));

        appointment.setStatus(appointmentStatus);

        appointmentRepository.save(appointment);

        emailService.sendConfirmAppointmentRequestEmail(
                appointment.getBookerEmail(),
                appointment.getBookerName(),
                appointment.getContent(),
                appointmentStatus == AppointmentStatus.APPROVED
                );


        return appointmentMapper.toAppointmentResponse(appointment);
    }
}
