package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.enums.Type;

public record ReytingDTO(
        Type type,
        Integer count,
        Float star
){
}
