package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.MessageType;

public record MessageDTO(
        String chatId,
        String from_id,
        String for_id,
        String text,
        CallDTO callDTO,
        MessageType messageType
) {
}
