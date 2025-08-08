package uz.husan.ordermanagment.dto.auth;

import lombok.Data;

@Data
public class ConfirmDTO {
    private String code;
    private String email;
}
