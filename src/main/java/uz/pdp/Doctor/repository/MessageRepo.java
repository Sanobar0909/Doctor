package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Message;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, String> {
    List<Message> findAllByChatIs(Chat chat);
}
