package uz.husan.ordermanagment.dto.auth;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
}
