package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Basket;

public interface BasketRepo extends JpaRepository<Basket,String> {
}
