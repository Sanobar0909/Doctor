package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,String> {
    Optional<Payment> findByUserId(String id);
}
