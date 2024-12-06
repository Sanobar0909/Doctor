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
import uz.pdp.Doctor.model.*;
import uz.pdp.Doctor.repository.PermissionRepo;
import uz.pdp.Doctor.repository.UserRepo;
import uz.pdp.Doctor.dto.AuthenticationDTO;
import uz.pdp.Doctor.dto.UserDTO;
import uz.pdp.Doctor.exception.EmailOrPasswordWrong;
import uz.pdp.Doctor.mapper.UserMapper;
import uz.pdp.Doctor.repository.FilesRepo;
import uz.pdp.Doctor.repository.RoleRepo;
import uz.pdp.Doctor.util.JwtUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TemporaryUserService temporaryUserService;
    private final JwtUtil jwtUtil;
    private final S3StorageService s3StorageService;
    private final VerificationService verificationService;
    private final String AWS_PUBLIC = "public";
    //    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";
    private final FilesRepo filesRepo;
    private final JavaMailSender javaMailSender;

    public String register(UserDTO userDTO, MultipartFile file) throws MessagingException, IOException {
        if (userRepo.findByEmail(userDTO.email()).isPresent()) {
            throw new EmailOrPasswordWrong("User with this email already exists.", HttpStatus.CONFLICT);
        }

        if (userDTO.password() == null || userDTO.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        String verificationCode = sendMail(userDTO.email());
        temporaryUserService.saveTemporaryUser(userDTO, file, verificationCode);
        return verificationCode;
    }

    public String saveUserAfterVerification(String email, String code) {
        TemporaryUser temporaryUser = temporaryUserService.getTemporaryUserByEmail(email);
        if (temporaryUser == null) {
            throw new IllegalArgumentException("No temporary user found for email: " + email);
        }

        if (!temporaryUser.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }

        User user = UserMapper.USER_MAPPER.toEntity(temporaryUser.getUserDTO());

        String encodedPassword = passwordEncoder.encode(temporaryUser.getUserDTO().password());
        user.setPassword(encodedPassword);

        // Save permissions first
        List<Permission> permissions = List.of(
                new Permission("CREATE"),
                new Permission("UPDATE"),
                new Permission("DELETE"),
                new Permission("GET"),
                new Permission("GET_ALL"),
                new Permission("LOCATION"),
                new Permission("GET_USER_LOCATION"),
                new Permission("UPDATE_LOCATION"),
                new Permission("MARK"),
                new Permission("ORDER"),
                new Permission("SHOW_CARD"),
                new Permission("BUY")
        );

        permissions.forEach(permissionRepo::save); // Save each Permission object

        Role newRole = new Role();
        newRole.setName("USER");
        newRole.setPermissions(permissions);

        Role savedRole = roleRepo.save(newRole); // Save Role after permissions are saved
        user.setRoles(Collections.singletonList(savedRole));

        if (temporaryUser.getFile() != null) {
            Files files = s3StorageService.save(temporaryUser.getFile(), AWS_PUBLIC);
            files.setUrl(AWS_URL + files.getPath());
            Files savedFile = filesRepo.save(files);
            user.setFiles(savedFile);
        } else {
            user.setFiles(null);
        }

        userRepo.save(user);
        temporaryUserService.removeTemporaryUser(email);
        return "User successfully verified and saved.";
    }


    public String sendMail(String recipientEmail) throws MessagingException, IOException {
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("arslonboyevasanobar09@gmail.com");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSubject("Verification Code");
        String content = "Your verification code is: " + verificationCode;
        mimeMessageHelper.setText(content, verificationCode);
        System.out.println(verificationCode);
        javaMailSender.send(mimeMessage);
        return verificationCode;
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

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            Optional<User> userOpt = userRepo.findByEmail(name);
            if (userOpt.isPresent()) {
                return userOpt.get();
            }
            throw new IllegalArgumentException("User not found");
        }
        throw new IllegalStateException("No authenticated user");
    }

    public String updateUserProfile(String fullName, MultipartFile file) {
        User user = getCurrentUser();
        boolean isUpdated = false;

        if (fullName != null && !fullName.isEmpty()) {
            user.setFull_name(fullName);
            isUpdated = true;
        }

        if (file != null && !file.isEmpty()) {
            Files files = s3StorageService.save(file, AWS_PUBLIC);
            files.setUrl(AWS_URL + files.getPath());
            Files savedFile = filesRepo.save(files);
            user.setFiles(savedFile);
            isUpdated = true;
        }

        if (isUpdated) {
            userRepo.save(user);
            return "Profile updated successfully.";
        }

        return "No changes were made.";
    }


    public void logout(String email) {

    }
}
