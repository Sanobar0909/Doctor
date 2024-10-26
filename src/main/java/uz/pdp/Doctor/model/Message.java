package uz.pdp.Doctor.model;

/*
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private String from_id;
    private String for_id;
    private String text;
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "call_id")
    private Call call;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;

    @Builder
    public Message(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, Chat chat, String from_id, String for_id, String text, LocalDate date, LocalTime time, Call call, Files files, MessageType messageType) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.chat = chat;
        this.from_id = from_id;
        this.for_id = for_id;
        this.text = text;
        this.date = date;
        this.time = time;
        this.call = call;
        this.files = files;
        this.messageType = messageType;
    }
}

*/
