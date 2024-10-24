package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "reyting_id")
    private Reyting reyting;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;

    @Builder
    public Doctor(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String last_name, String first_name, DoctorCategory category, Reyting reyting, String email, String password, Files files) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.last_name = last_name;
        this.first_name = first_name;
        this.category = category;
        this.reyting = reyting;
        this.email = email;
        this.password = password;
        this.files = files;
    }
}
