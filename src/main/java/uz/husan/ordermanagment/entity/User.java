package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.entity.enums.Specialization;

import java.util.Collection;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends AbsEntity implements UserDetails {
    private String fullName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;
    private String confCode;
    //private Double balance;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
