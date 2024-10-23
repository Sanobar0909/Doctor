package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Payment;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,String> {
    Optional<Payment> findByUserId(String id);
}
