package uz.husan.ordermanagment.resetPassword;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.PasswordResetToken;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.PasswordResetTokenRepository;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.impl.AuthServiceImpl;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServices {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl emailService;

    public ResponseMessage forgotPassword(String email) {
        boolean b = userRepository.existsByEmail(email);
        if(!b){
           return ResponseMessage.builder().
                    success(false).
                    message("Bunaqa User tizimda mavjud emas").
                    data(email).
                    build();
        }

        userRepository.findByEmail(email).ifPresent(user -> {

            tokenRepository.deleteByUser(user);

            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            tokenRepository.save(resetToken);
            emailService.emailService(email,token);

        });

        return ResponseMessage.builder().
                success(true).
                message("sent").
                data(email).
                build();
    }

    public ResponseMessage resetPassword(String token, String newPassword1,String newPassword2) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseMessage.builder().
                    success(false).
                    message("Token expired").
                    data(token).
                    build();
        }

        User user = resetToken.getUser();
        if(!newPassword1.equals(newPassword2)){
            return ResponseMessage.builder().
                    success(false).
                    message("Kiritilayotgan yangi parollar bir xil bolishi kerak").
                    data(newPassword1 +" va "+newPassword2 ).
                    build();
        }
        user.setPassword(passwordEncoder.encode(newPassword1));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
        return ResponseMessage.builder().
                success(true).
                message("succesfuly reset password").
                data("succes").
                build();
    }
}
