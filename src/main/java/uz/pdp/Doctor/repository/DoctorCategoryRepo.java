package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.DoctorCategory;

import java.util.Optional;

public interface DoctorCategoryRepo extends JpaRepository<DoctorCategory, String> {
    Optional<DoctorCategory> findByName(String name);
}
