package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends BaseEntity{
    private String last_name;
    private String first_name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private DoctorCategory category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;

    @Builder
    public Doctor(String last_name, String first_name, DoctorCategory category, List<Rating> ratings, String email, String password, Files files) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.category = category;
        this.ratings = ratings;
        this.email = email;
        this.password = password;
        this.files = files;
    }

    public double calculateAverageRating() {
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
