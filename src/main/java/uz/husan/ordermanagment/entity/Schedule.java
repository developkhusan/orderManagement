package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.husan.ordermanagment.entity.enums.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends AbsEntity {


    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status; // OPEN, CLOSED
}
