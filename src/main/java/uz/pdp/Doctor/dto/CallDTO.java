package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.CallType;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public record CallDTO(
        String chatId,
        CallType type
) {
}
