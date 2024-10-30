package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.repository.AppointmentRepo;
import uz.pdp.Doctor.repository.DoctorRepo;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
}
