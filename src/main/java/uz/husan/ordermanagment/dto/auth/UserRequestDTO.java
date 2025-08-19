package uz.husan.ordermanagment.dto.auth;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String userLocation;
    private String imageUrl;
}
