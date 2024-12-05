package uz.pdp.Doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.Doctor.dto.AppointmentDTO;
import uz.pdp.Doctor.model.Appointment;
import uz.pdp.Doctor.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/regist_day")
    public ResponseEntity<Appointment> regist_day(AppointmentDTO appointmentDTO){
        Appointment appointment = appointmentService.create(appointmentDTO);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/getAllAppointment/{doctorId}")
    public ResponseEntity<List<Appointment>> getAll(@PathVariable("doctorId") String doctorId){
        List<Appointment> appointments = appointmentService.getAllAppointmentByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> update(@PathVariable("id") String id, AppointmentDTO appointmentDTO){
        Appointment update = appointmentService.update(id, appointmentDTO);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") String id){
        appointmentService.remove(id);
        return ResponseEntity.ok("Deleted appointment");
    }
}
