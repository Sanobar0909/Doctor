package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.Doctor.enums.RatingType;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class Rating extends BaseEntity{
    private float star;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatingType type;
    private int score;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "arcticle_id")
    private Arcticle arcticle;

    @Builder
    public Rating(float star,RatingType type, int score, Doctor doctor, Product product, Arcticle arcticle) {
        this.star = star;
        this.type = type;
        this.score = score;
        this.doctor = doctor;
        this.product = product;
        this.arcticle = arcticle;
    }
}
