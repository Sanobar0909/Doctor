package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Order;

public interface OrderRepo extends JpaRepository<Order,String> {
}
