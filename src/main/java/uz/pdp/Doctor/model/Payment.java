package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{
    private Integer concultation;
    private Integer other_expenses;
    private Integer card_number;
}
