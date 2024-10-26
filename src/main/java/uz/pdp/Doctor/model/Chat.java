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
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
public class Chat extends BaseEntity{
   /* @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

   /* @Builder
    public Chat(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, Doctor doctor, User user) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.doctor = doctor;
        this.user = user;
    }*/
}

