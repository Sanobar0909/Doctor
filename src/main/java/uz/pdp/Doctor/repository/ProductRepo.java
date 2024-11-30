package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
    List<Product> findAllByNameContaining(String name);
}
