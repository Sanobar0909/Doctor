package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.enums.RatingType;
import uz.pdp.Doctor.model.Rating;

@Repository
public interface RatingRepo extends JpaRepository<Rating, String > {
    Rating findByStarAndType(float star, RatingType type);
}
