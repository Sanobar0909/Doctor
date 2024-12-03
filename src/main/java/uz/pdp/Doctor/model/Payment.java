package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

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
    private String card_number;
    private String status; // PENDING, COMPLETED
    private LocalDateTime paymentDate;
}
