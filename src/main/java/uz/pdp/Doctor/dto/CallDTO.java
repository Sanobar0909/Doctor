package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.CallType;

import java.sql.Time;

public record CallDTO(
        String chatId,
        CallType type,
        Time talk
) {
}
