package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity{
    private String name;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files icon;

    @Builder
    public Category(String name, Files icon) {
        this.name = name;
        this.icon = icon;
    }
}
