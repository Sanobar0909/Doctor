package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double subtotals;
    private double other_expenses;
    private double totals;
    private Integer card_number;
}
