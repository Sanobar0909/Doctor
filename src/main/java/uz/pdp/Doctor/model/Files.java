package uz.pdp.Doctor.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files extends BaseEntity {
    private String path;
    private String url;
}
