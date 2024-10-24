package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.CallType;
import uz.pdp.Doctor.model.Chat;

import java.sql.Time;

public record CallDTO(
        Chat chat,
        CallType type,
        Time Talk
) {
}
