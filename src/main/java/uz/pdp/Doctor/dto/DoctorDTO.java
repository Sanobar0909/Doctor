package uz.pdp.Doctor.dto;

import uz.pdp.Doctor.model.DoctorCategory;
import uz.pdp.Doctor.model.Reyting;

public record DoctorDTO (
        String last_name,
        String first_name,
        String categoryId,
        String email,
        String password
){
}
