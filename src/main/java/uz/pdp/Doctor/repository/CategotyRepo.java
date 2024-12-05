package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Category;

import java.util.Optional;

public interface CategotyRepo extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
}
