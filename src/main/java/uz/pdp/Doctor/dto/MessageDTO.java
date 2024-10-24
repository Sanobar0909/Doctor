package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.MessageType;
import uz.pdp.Doctor.model.Chat;

import java.time.LocalDate;
import java.time.LocalTime;

public record MessageDTO (
        String chatId,
        String from_id,
        String for_id,
        String text,
        CallDTO callDTO,
        MessageType messageType
){
}
