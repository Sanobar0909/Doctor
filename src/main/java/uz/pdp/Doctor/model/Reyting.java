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
public class Reyting extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReytingType type;
    private Integer count;
    private float star;

    @Builder
    public Reyting(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, ReytingType type, Integer count, float star) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.type = type;
        this.count = count;
        this.star = star;
    }
}
