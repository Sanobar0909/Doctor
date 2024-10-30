package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Appointment;
import uz.pdp.Doctor.model.Doctor;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, String> {
    List<Appointment> findAllByDoctorIs(Doctor doctor);
}
