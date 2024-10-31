package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.Doctor.enums.ReytingType;

import java.time.LocalDateTime;

@Entity
@Table(name = "reyting")
@Getter
@Setter
@NoArgsConstructor
public class Rating extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReytingType type;
    private int score;
    private String from_id;

    @Builder
    public Rating(ReytingType type, int score, String from_id) {
        this.type = type;
        this.score = score;
        this.from_id = from_id;
    }
}
