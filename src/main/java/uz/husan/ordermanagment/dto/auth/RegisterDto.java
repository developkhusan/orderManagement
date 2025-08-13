package uz.husan.ordermanagment.dto.auth;

import lombok.Data;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.entity.enums.Specialization;

@Data
public class RegisterDto {
    private String fullName;
    private String email; // bu email bo‘ladi
    private String password;
    private Role role; // ADMIN, DOCTOR, USER
    private Specialization specialization; // faqat DOCTOR bo‘lsa
    private Long hospitalId; // faqat DOCTOR bo‘lsa
}

