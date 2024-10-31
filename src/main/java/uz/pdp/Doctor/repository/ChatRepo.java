package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.User;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, String> {
    List<Chat> findAllByUserIs(User user);
    List<Chat> findAllByDoctorIs(Doctor doctor);
}
