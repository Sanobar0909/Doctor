package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.ReytingType;

public record ReytingDTO (
        ReytingType type,
        float star
){
}
