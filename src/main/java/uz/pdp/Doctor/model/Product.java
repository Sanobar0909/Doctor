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
public class Product extends BaseEntity{
    private String name;
    private Integer weight;
    private Long price;
    private String description;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;
}
