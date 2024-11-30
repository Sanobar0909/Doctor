package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Double price;
    private Integer weight;
}
