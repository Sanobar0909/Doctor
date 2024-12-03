package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.Doctor.enums.CallType;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "call")
@Getter
@Setter
@NoArgsConstructor
public class Call extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallType type;
    private LocalDate date;
    private LocalTime time;

    @Builder
    public Call(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, Chat chat, CallType type, LocalDate date, LocalTime time) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.chat = chat;
        this.type = type;
        this.date = date;
        this.time = time;
    }
}
