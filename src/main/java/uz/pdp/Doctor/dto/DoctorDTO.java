package uz.pdp.Doctor.dto;

public record DoctorDTO (
        String last_name,
        String first_name,
        String categoryId,
        String email,
        String password
){
}
