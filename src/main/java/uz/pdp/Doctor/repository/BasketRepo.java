package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Basket;

import java.util.Optional;

@Repository
public interface BasketRepo extends JpaRepository<Basket,String> {
    Optional<Basket> findByUserId(String userId);
}
