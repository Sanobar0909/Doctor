package uz.pdp.Doctor.service;

import org.springframework.stereotype.Service;
import uz.pdp.Doctor.dto.UserDTO;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationService {
    private final ConcurrentHashMap<String, UserDTO> pendingUsers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void saveVerificationDetails(String email, String code, UserDTO userDTO) {
        verificationCodes.put(email, code);
        pendingUsers.put(email, userDTO);
    }

    public UserDTO getPendingUser(String email) {
        return pendingUsers.get(email);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    public void removeVerificationDetails(String email) {
        verificationCodes.remove(email);
        pendingUsers.remove(email);
    }
}
