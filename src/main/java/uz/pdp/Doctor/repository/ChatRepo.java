package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.User;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, String> {
    List<Chat> findAllByUserIs(User user);
    List<Chat> findAllByDoctorIs(Doctor doctor);
}
