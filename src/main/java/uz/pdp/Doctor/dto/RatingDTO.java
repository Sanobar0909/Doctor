package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.RatingType;

public record RatingDTO(
        RatingType type,
        int score,
        String fromId
){
}
