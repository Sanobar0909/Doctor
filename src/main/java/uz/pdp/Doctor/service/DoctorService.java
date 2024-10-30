package uz.pdp.Doctor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
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
import uz.pdp.Doctor.dto.DoctorDTO;
import uz.pdp.Doctor.exception.EmailOrPasswordWrong;
import uz.pdp.Doctor.mapper.DoctorMapper;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.FilesRepo;
import uz.pdp.Doctor.util.JwtUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";
    private final FilesRepo filesRepo;
    private final JavaMailSender javaMailSender;

    public DoctorService(DoctorRepo doctorRepo, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, S3StorageService s3StorageService, FilesRepo filesRepo, JavaMailSender javaMailSender
    ) {
        this.doctorRepo = doctorRepo;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.s3StorageService = s3StorageService;
        this.filesRepo = filesRepo;
        this.javaMailSender = javaMailSender;
    }

    public Doctor register(DoctorDTO doctorDTO, MultipartFile file) {
        if (doctorRepo.findByEmail(doctorDTO.email()).isPresent()) {
            throw new EmailOrPasswordWrong("Doctor with this email already exists.", HttpStatus.CONFLICT);
        }

        if (doctorDTO.password() == null || doctorDTO.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        Doctor doctor = DoctorMapper.DOCTOR_MAPPER.toEntity(doctorDTO);

        String encodedPassword = passwordEncoder.encode(doctorDTO.password());
        doctor.setPassword(encodedPassword);

        Files files = s3StorageService.saveImage(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        doctor.setFiles(savedFile);
        return doctorRepo.save(doctor);
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

    public Optional<Doctor> getCurrentDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            return doctorRepo.findByEmail(name);
        }
        return Optional.empty();
    }
}
