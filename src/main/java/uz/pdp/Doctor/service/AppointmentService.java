package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.dto.AppointmentDTO;
import uz.pdp.Doctor.model.Appointment;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.repository.AppointmentRepo;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.FilesRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;

    public Appointment create(AppointmentDTO appointmentDTO){
        Doctor doctor = doctorRepo.findById(appointmentDTO.doctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Appointment build = Appointment.builder().date(appointmentDTO.date())
                .time(appointmentDTO.time())
                .day(appointmentDTO.day())
                .doctor(doctor).build();
        return appointmentRepo.save(build);
    }

    public Appointment update(String id, AppointmentDTO appointmentDTO){
        Doctor doctor = doctorRepo.findById(appointmentDTO.doctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appointment.setDate(appointmentDTO.date());
        appointment.setTime(appointmentDTO.time());
        appointment.setDay(appointmentDTO.day());
        appointment.setDoctor(doctor);
        return appointment;
    }

    public List<Appointment> getAllAppointmentByDoctorId(String doctorId){
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        return appointmentRepo.findAllByDoctorIs(doctor);
    }

    public void remove(String id){
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appointmentRepo.delete(appointment);
    }
}
