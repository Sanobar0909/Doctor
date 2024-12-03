package uz.pdp.Doctor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends BaseEntity {
    private String full_name;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    @Builder
    public User(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String full_name, String email, String password, Files files, List<Role> roles) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.files = files;
        this.roles = (roles != null) ? roles : new ArrayList<>();
    }
}
