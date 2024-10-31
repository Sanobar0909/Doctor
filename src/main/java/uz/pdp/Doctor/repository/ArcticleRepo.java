package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Arcticle;
import uz.pdp.Doctor.model.Doctor;

import java.util.List;

public interface ArcticleRepo extends JpaRepository<Arcticle, String> {
    List<Arcticle> findAllByNameContaining(String name);
}
