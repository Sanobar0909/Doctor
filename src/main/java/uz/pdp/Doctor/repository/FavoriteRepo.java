package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Favorite;
import uz.pdp.Doctor.model.Product;

import java.util.Optional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, String> {
    Optional<Favorite> findByProduct(Product product);
}
