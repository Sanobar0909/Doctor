package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.Doctor.model.Appointment;
import uz.pdp.Doctor.model.Doctor;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, String> {
    List<Appointment> findAllByDoctorIs(Doctor doctor);
}
