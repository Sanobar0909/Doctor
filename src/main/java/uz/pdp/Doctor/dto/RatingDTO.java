package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.ReytingType;

public record RatingDTO(
        ReytingType type,
        int score,
        String fromId
){
}
