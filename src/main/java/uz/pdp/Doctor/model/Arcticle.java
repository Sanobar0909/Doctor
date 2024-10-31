package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "arcticle")
@Getter
@Setter
@NoArgsConstructor
public class Arcticle extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Doctor author;
    private String name;
    private String desc;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files image;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @Builder
    public Arcticle(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, Doctor author, String name, String desc, LocalDate date, Files image, List<Rating> ratings) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.author = author;
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.image = image;
        this.ratings = ratings;
    }

    public double calculateAverageRating() {
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
