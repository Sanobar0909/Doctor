package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.DoctorCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findAllByCategory(DoctorCategory category);
}
