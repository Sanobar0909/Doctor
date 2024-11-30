package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,String> {
    Optional<Role> findByName(String name);
}
