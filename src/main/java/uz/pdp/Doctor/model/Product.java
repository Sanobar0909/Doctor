package uz.pdp.Doctor.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;

    public double calculateAverageRating() {
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
