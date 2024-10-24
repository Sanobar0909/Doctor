package uz.pdp.Doctor.dto;

public record AddressDTO (
        String street,
        double latitude,
        double langitude,
        String userId
){
}
