package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.husan.ordermanagment.entity.enums.AppointmentStatus;
import uz.husan.ordermanagment.entity.enums.VisitType;

import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    private LocalTime appointmentTime; // Masalan: 09:00

    @Enumerated(EnumType.STRING)
    private VisitType visitType; // CONSULTATION, CHECKUP, SURGERY

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Integer durationMinutes; // visitType ga qarab avtomatik set qilinadi
}
