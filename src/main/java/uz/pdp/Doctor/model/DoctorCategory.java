package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_category")
@Getter
@Setter
@NoArgsConstructor
public class DoctorCategory extends BaseEntity{
    private String name;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files icon;

    @Builder
    public DoctorCategory(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String name, Files icon) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.name = name;
        this.icon = icon;
    }
}
