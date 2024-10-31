package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.enums.ReytingType;
import uz.pdp.Doctor.model.Rating;

@Repository
public interface ReytingRepo extends JpaRepository<Rating, String> {
    Rating findByStarAndType(float star, ReytingType reytingType);
}
