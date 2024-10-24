package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.Doctor.enums.WeekDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
public class Appointment extends BaseEntity{
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
        private WeekDay day;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Builder
    public Appointment(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, LocalDate date, LocalTime time, WeekDay day, Doctor doctor) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.date = date;
        this.time = time;
        this.day = day;
        this.doctor = doctor;
    }
}
