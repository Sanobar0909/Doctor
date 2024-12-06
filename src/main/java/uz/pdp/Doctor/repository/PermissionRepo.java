package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Permission;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, String> {
}
