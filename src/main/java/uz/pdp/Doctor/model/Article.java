package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.awt.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article extends BaseEntity{
    private String title;
    private String content;
    private String author_id;
    private LocalDate publish_date;
    private String status;
    private Image image;

}
