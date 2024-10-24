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
    private double subtotals;
    private double other_expenses;
    private double totals;
    private Integer card_number;
}
