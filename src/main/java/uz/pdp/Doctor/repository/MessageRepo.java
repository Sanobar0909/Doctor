package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, String> {
    List<Message> findAllByChatIs(Chat chat);
}
