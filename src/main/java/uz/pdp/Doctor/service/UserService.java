package uz.pdp.Doctor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.AuthenticationDTO;
import uz.pdp.Doctor.dto.UserDTO;
import uz.pdp.Doctor.exception.EmailOrPasswordWrong;
import uz.pdp.Doctor.mapper.UserMapper;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.model.Role;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.FilesRepo;
import uz.pdp.Doctor.repository.RoleRepo;
import uz.pdp.Doctor.repository.UserRepo;
import uz.pdp.Doctor.util.JwtUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";
    private final FilesRepo filesRepo;
    private final JavaMailSender javaMailSender;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, S3StorageService s3StorageService, FilesRepo filesRepo, JavaMailSender javaMailSender) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.s3StorageService = s3StorageService;
        this.filesRepo = filesRepo;
        this.javaMailSender = javaMailSender;
    }

    public User register(UserDTO userDTO, MultipartFile file) {
        if (userRepo.findByEmail(userDTO.email()).isPresent()) {
            throw new EmailOrPasswordWrong("User with this email already exists.", HttpStatus.CONFLICT);
        }

        if (userDTO.password() == null || userDTO.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        User user = UserMapper.USER_MAPPER.toEntity(userDTO);

        String encodedPassword = passwordEncoder.encode(userDTO.password());
        user.setPassword(encodedPassword);

        Role newRole = new Role();
        newRole.setName("USER");
        Role savedRole = roleRepo.save(newRole);
        user.setRoles(Collections.singletonList(savedRole));

        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        user.setFiles(savedFile);
        return userRepo.save(user);
    }

    public ResponseEntity<AuthenticationDTO> authentication(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtil.generateTokenAccess(email);
            String refreshToken = jwtUtil.generateTokenRefresh(email);
            refreshTokenService.createRefreshToken(email);
            return ResponseEntity.ok(new AuthenticationDTO(accessToken, refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthenticationDTO("Invalid credentials"));
        }
    }

    public void sendMail(String recipientEmail) throws MessagingException, IOException {
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("arslonboyevasanobar09@gmail.com");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSubject("Verification Code");
        String content = "Your verification code is: " + verificationCode;
        mimeMessageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            return userRepo.findByEmail(name);
        }
        return Optional.empty();
    }
}
