package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {
}
