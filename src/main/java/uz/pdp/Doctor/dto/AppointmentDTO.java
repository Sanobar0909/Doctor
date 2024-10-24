package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.WeekDay;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentDTO(
        LocalDate date,
        LocalTime time,
        WeekDay day,
        String doctorId
) {
}
