package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Arcticle;

import java.util.List;

@Repository
public interface ArcticleRepo extends JpaRepository<Arcticle, String> {
    List<Arcticle> findAllByNameContaining(String name);
}
