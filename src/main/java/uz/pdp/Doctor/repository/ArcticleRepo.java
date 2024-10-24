package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Arcticle;

import java.util.List;

public interface ArcticleRepo extends JpaRepository<Arcticle, String> {
    List<Arcticle> findAllByReytingStar(float star);
}
