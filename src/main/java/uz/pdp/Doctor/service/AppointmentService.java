package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.repository.AppointmentRepo;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.FilesRepo;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final FilesRepo filesRepo;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;


}
