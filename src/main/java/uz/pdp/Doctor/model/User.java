package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    private String full_name;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Builder
    public User(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String full_name, String email, String password, List<Role> roles) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.roles = (roles != null) ? roles : new ArrayList<>();
    }
}
