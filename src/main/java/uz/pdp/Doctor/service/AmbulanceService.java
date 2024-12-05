package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.dto.AddressDTO;
import uz.pdp.Doctor.model.Address;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.AmbulanceRepo;
import uz.pdp.Doctor.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class AmbulanceService {
    private final AmbulanceRepo ambulanceRepo;
    private final UserService userService;
    private final UserRepo userRepo;

    public Address create(AddressDTO addressDTO) {
        User user = userRepo.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Address address = Address.builder()
                .street(addressDTO.street())
                .latitude(addressDTO.latitude())
                .langitude(addressDTO.langitude())
                .user(user)
                .build();
        return ambulanceRepo.save(address);
    }

    public Address getUserAddress() {
        User user = userService.getCurrentUser();
        Address address = ambulanceRepo.findAddressByUser_Id(user.getId());
        return address;
    }

    public Address update(AddressDTO addressDTO) {
        Address address = ambulanceRepo.findById(addressDTO.id()).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        address.setStreet(addressDTO.street());
        address.setLatitude(addressDTO.latitude());
        address.setLangitude(addressDTO.langitude());
        return ambulanceRepo.save(address);
    }
}
