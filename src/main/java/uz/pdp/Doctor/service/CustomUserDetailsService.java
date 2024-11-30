package uz.pdp.Doctor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.UserRepo;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final DoctorRepo doctorRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo, DoctorRepo doctorRepo) {
        this.userRepo = userRepo;
        this.doctorRepo = doctorRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        Optional<Doctor> doctor = doctorRepo.findById(user.get().getId());
        if (!doctor.isEmpty()){
            doctor.orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
            UserDetails build = org.springframework.security.core.userdetails.User.withUsername(email)
                    .password(doctor.get().getPassword())
                    .roles("DOCTOR")
                    .build();
            return build;
        }
        user.orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
        UserDetails build = org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.get().getPassword())
                .roles("ADMIN")
                .build();
        return build;

//        if (doctor!=null){
//            Set<GrantedAuthority> authorities = user.getRoles().stream()
//                    .map(role -> new SimpleGrantedAuthority(role.getName()))
//                    .collect(Collectors.toSet());
//            return new CustomUserDetails(doctor.getEmail(), doctor.getPassword(), authorities);
//        }
//        Set<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());
//
//        return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
    }
}
