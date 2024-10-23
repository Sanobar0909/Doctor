package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Product;

public interface ProductRepo extends JpaRepository<Product, String> {
}
