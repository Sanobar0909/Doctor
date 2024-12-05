package uz.pdp.Doctor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Role;
import uz.pdp.Doctor.model.User;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (user.isEmpty()) {
            throw new BadCredentialsException("Username or password incorrect");
        }

        List<Role> roles = user.get().getRoles();
        if (roles.isEmpty()) {
            throw new BadCredentialsException("No roles assigned to user");
        }

        List<GrantedAuthority> authorities = roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getName()))
                .collect(Collectors.toList());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.get().getPassword())
                .authorities(authorities)
                .build();

        return userDetails;
    }
}

