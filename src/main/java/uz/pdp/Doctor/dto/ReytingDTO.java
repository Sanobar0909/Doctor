package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.ReytingType;
import uz.pdp.Doctor.enums.Type;

public record ReytingDTO(
        ReytingType type,
        float star
){
}
