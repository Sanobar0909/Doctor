package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Files;

public interface ImagesRepo extends JpaRepository<Files,String> {
    Files findImageById(String imageId);
}
