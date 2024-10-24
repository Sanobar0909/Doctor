package uz.pdp.Doctor.dto;

public record ProductDTO(
        String name,
        Integer weight,
        Long price,
        String description
) {
}
