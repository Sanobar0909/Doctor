package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.model.Payment;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.PaymentRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepo paymentRepo;

//    public Optional<Payment> getPaymentForUser(User user) {
//        return paymentRepo.findByUserId(user.getId());
//    }
}
