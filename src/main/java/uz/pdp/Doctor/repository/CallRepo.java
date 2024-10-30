package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Call;
import uz.pdp.Doctor.model.Chat;

import java.util.List;

public interface CallRepo extends JpaRepository<Call, String> {
    List<Call> findAllByChatIs(Chat chat);
}
