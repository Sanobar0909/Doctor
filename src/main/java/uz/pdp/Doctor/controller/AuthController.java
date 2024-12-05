package uz.pdp.Doctor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.AuthenticationDTO;
import uz.pdp.Doctor.dto.UserDTO;
import uz.pdp.Doctor.model.Role;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.service.UserService;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Sign in and sign up here")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User Registration", description = "Welcome! Please use this endpoint to register a new user. You can upload a profile picture.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@RequestParam("full_name") String full_name,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam(required = false, value = "files") MultipartFile file) throws MessagingException, IOException {
        UserDTO userDTO = new UserDTO(full_name, email, password);
        userService.register(userDTO, file);
        return ResponseEntity.ok(userService.sendMail(email));
    }

    @Operation(summary = "User Authentication", description = "Welcome back! Use this endpoint to sign in with your email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> authentication(@RequestParam("email") String email, @RequestParam("password") String password) {
        ResponseEntity<AuthenticationDTO> authentication = userService.authentication(email, password);
        return authentication;
    }

    @Operation(summary = "User forgot password", description = "Welcome back!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/configuration-code")
    public ResponseEntity<String> sendEmail(@RequestParam("code") String code, @RequestParam("email") String email) throws MessagingException, IOException {
        String verificationCode = userService.sendMail(email);
        if (Objects.equals(verificationCode, code)) {
            userService.saveUserAfterVerification(email);
            return ResponseEntity.ok("Successfully verified and user saved");
        }
        return ResponseEntity.status(401).body("Invalid verification code");
    }

    @Operation(summary = "View Profile", description = "View the details of the logged-in user's profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/profile")
    public ResponseEntity<User> viewProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update Profile", description = "Update the profile information of the logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PutMapping(value = "/Update_profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfile(@RequestParam(required = false, value = "full_name") String fullName,
                                                @RequestParam(required = false, value = "file") MultipartFile file) throws IOException {
        userService.updateUserProfile(fullName, file);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @Operation(summary = "Logout", description = "Log out the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out"),
            @ApiResponse(responseCode = "401", description = "User not logged in")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("email") String email) {
        userService.logout(email);
        return ResponseEntity.ok("Successfully logged out");
    }
}
