package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reyting extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer count;
    private Float star;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
