package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Address;

public interface AmbulanceRepo extends JpaRepository<Address, String> {
    Address findAddressByUser_Id(String id);
}
