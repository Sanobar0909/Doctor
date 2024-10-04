package uz.pdp.Doctor.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.Doctor.dto.AuthenticationDTO;
import uz.pdp.Doctor.dto.UserDTO;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.service.UserService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Login", description = "Sign in and sign up here")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationDTO> authentication(@RequestParam("email") String email,@RequestParam("password") String password) {
        ResponseEntity<AuthenticationDTO> authentication = userService.authentication(email, password);
        return authentication;
    }
}
