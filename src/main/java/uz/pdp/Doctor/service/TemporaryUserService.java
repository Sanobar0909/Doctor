package uz.pdp.Doctor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.UserDTO;
import uz.pdp.Doctor.model.TemporaryUser;

import java.util.HashMap;
import java.util.Map;

@Service
public class TemporaryUserService {
    private final Map<String, TemporaryUser> temporaryUsers = new HashMap<>();

    public void saveTemporaryUser(UserDTO userDTO, MultipartFile file, String verificationCode) {
        TemporaryUser temporaryUser = new TemporaryUser(userDTO, file, verificationCode);
        temporaryUsers.put(userDTO.email(), temporaryUser);
    }

    public TemporaryUser getTemporaryUserByEmail(String email) {
        return temporaryUsers.get(email);
    }

    public void removeTemporaryUser(String email) {
        temporaryUsers.remove(email);
    }
}
