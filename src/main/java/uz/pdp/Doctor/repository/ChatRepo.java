package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.User;

import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat, String> {
    Optional<Chat> findAllByUserIs(User user);
    Optional<Chat> findAllByDoctorIs(Doctor doctor);
}
