package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "arcticle")
@Getter
@Setter
@NoArgsConstructor
public class Article extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Doctor author;
    private String name;
    private String desc;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files image;
    @ManyToOne
    @JoinColumn(name = "reyting_id")
    private Reyting reyting;

    @Builder
    public Article(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, Doctor author, String name, String desc, LocalDate date, Files image, Reyting reyting) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.author = author;
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.image = image;
        this.reyting = reyting;
    }
}
