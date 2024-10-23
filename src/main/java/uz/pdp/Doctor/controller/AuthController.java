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
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.service.UserService;

import java.io.IOException;

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
                                         @RequestParam("files") MultipartFile file) {
        UserDTO userDTO = new UserDTO(full_name, email, password);
        userService.register(userDTO,file);
        return ResponseEntity.ok("Successful");
    }

    @Operation(summary = "User Authentication", description = "Welcome back! Use this endpoint to sign in with your email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationDTO> authentication(@RequestParam("email") String email, @RequestParam("password") String password) {
        ResponseEntity<AuthenticationDTO> authentication = userService.authentication(email, password);
        return authentication;
    }

    @Operation(summary = "User forgot password", description = "Welcome back!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/send-email")
    public void sendEmail(@RequestParam("email") String email) throws MessagingException, IOException {
        userService.sendMail(email);
    }
}
