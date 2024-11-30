package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Files;

@Repository
public interface FilesRepo extends JpaRepository<Files,String> {
    Files findImageById(String imageId);
}
