package com.tvd.petcare.entities;

import com.tvd.petcare.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "appointments")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "booker_name")
    String bookerName;

    @Column(name = "booker_phone_number")
    String bookerPhoneNumber;

    @Column(name = "booker_email")
    String bookerEmail;

    @Column(columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    AppointmentStatus status;

    // @Column(name = "last_update_note")
    // String lastUpdateNote;
}
