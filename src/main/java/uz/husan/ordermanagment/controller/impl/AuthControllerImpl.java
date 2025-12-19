package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.AuthController;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.resetPassword.PasswordResetServices;
import uz.husan.ordermanagment.service.AuthService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final PasswordResetServices passwordResetServices;

    @Override
    public ResponseEntity<?> signup(UserRequestDTO userRequestDTO) {
        ResponseMessage signupResponse = authService.signup(userRequestDTO);
       return ResponseEntity.status(signupResponse.getSuccess()?200:400).body(signupResponse);
    }

    @Override
    public ResponseEntity<?> signin(EmailAndPasswordDTO emailAndPasswordDTO) {
        ResponseMessage signin = authService.signin(emailAndPasswordDTO);
        return ResponseEntity.status(signin.getSuccess()?200:400).body(signin);
    }

    @Override
    public ResponseEntity<?> confirm(String code, String email) {
        ResponseMessage confirmResponse = authService.confirm(code,email);
        return ResponseEntity.status(confirmResponse.getSuccess()?200:400).body(confirmResponse);
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        ResponseMessage responseMessage= passwordResetServices.forgotPassword(email);
        return ResponseEntity.status(responseMessage.getSuccess()?200:400).body(responseMessage);

    }

    @Override
    public ResponseEntity<String> resetPassword(String token, String newPassword1,String newPassword2) {
        ResponseMessage resetPassword=passwordResetServices.resetPassword(token,newPassword1,newPassword2);
        return ResponseEntity.status(resetPassword.getSuccess()?200:400).body(resetPassword.getMessage());
    }
}
