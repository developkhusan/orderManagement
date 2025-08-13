package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.AuthController;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.RegisterDto;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<?> signup(RegisterDto registerDto) {
        ResponseMessage signupResponse = authService.signup(registerDto);
        return ResponseEntity
                .status(signupResponse.getSuccess() ? 200 : 400)
                .body(signupResponse);
    }

    @Override
    public ResponseEntity<?> signin(EmailAndPasswordDTO emailAndPasswordDTO) {
        ResponseMessage signinResponse = authService.signin(emailAndPasswordDTO);
        return ResponseEntity
                .status(signinResponse.getSuccess() ? 200 : 400)
                .body(signinResponse);
    }

    @Override
    public ResponseEntity<?> confirm(String code, String email) {
        ResponseMessage confirmResponse = authService.confirm(code, email);
        return ResponseEntity
                .status(confirmResponse.getSuccess() ? 200 : 400)
                .body(confirmResponse);
    }
}
