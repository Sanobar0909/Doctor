package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {
    List<Product> findAllByNameContaining(String name);
}
