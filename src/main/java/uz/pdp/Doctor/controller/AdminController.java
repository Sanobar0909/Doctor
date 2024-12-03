package uz.pdp.Doctor.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Role;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.RoleRepo;
import uz.pdp.Doctor.repository.UserRepo;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Here it only changes user to doctor and doctor to user.")
public class AdminController {

    private final UserRepo userRepo;
    private final DoctorRepo doctorRepo;
    private final RoleRepo roleRepo;

    public AdminController(UserRepo userRepo, DoctorRepo doctorRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.doctorRepo = doctorRepo;
        this.roleRepo = roleRepo;
    }

    @GetMapping("/change")
    public ResponseEntity<?> changeUserRole(@RequestParam String userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Optional<Role> userRole = user.getRoles().stream()
                .filter(role -> role.getName().equals("USER"))
                .findFirst();
        Role role = userRole.get();
        role.setName("DOCTOR");
        roleRepo.save(role);

        Doctor doctor = Doctor.builder()
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .password(user.getPassword())
                .files(user.getFiles())
                .build();
        doctorRepo.save(doctor);

        return ResponseEntity.ok("User role updated to DOCTOR.");
    }
}



