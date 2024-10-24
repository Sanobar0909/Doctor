package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.DoctorCategory;
import uz.pdp.Doctor.model.User;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, String> {
    Optional<User> findByEmail(String email);
    List<Doctor> findAllByCategory(String category);
}
