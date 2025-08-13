package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.RegisterDto;
import uz.husan.ordermanagment.entity.Hospital;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.HospitalRepository;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.AuthService;
import uz.husan.ordermanagment.service.JWTService;

import java.util.Base64;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final JavaMailSender mailSender;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseMessage signup(RegisterDto dto) {
        // Email borligini tekshirish
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User already exists")
                    .build();
        }

        // Agar DOCTOR bo‘lsa, specialization va hospitalId majburiy
        if (dto.getRole() == Role.ROLE_DOCTOR) {
            if (dto.getSpecialization() == null || dto.getHospitalId() == null) {
                return ResponseMessage.builder()
                        .success(false)
                        .message("Doctor must have specialization and hospitalId")
                        .build();
            }
        }

        // User yaratish
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(false);
        user.setRole(dto.getRole());

        // DOCTOR bo‘lsa, hospital va specialization qo‘shish
        if (dto.getRole() == Role.ROLE_DOCTOR) {
            Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));
            user.setHospital(hospital);
            user.setSpecialization(dto.getSpecialization());
        }

        // Tasdiqlash kodi
        String code = generateCode();
        user.setConfCode(code);

        userRepository.save(user);
        sendVerificationCode(user.getEmail(), code);

        String encodedEmail = Base64.getEncoder().encodeToString(user.getEmail().getBytes());

        return ResponseMessage.builder()
                .success(true)
                .message("Sign up successful. Please check your email.")
                .data(encodedEmail)
                .build();
    }

    @Override
    public ResponseMessage signin(EmailAndPasswordDTO emailAndPasswordDTO) {
        User user = userRepository.findByEmail(emailAndPasswordDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(emailAndPasswordDTO.getPassword(), user.getPassword())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Email or password incorrect")
                    .build();
        }

        if (!user.getEnabled()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Please confirm your email")
                    .build();
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseMessage.builder()
                .success(true)
                .message("Sign in successful")
                .data(token)
                .build();
    }

    @Override
    public ResponseMessage confirm(String code, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean validCode = userRepository.existsByEmailAndConfCode(email, code);
        if (!validCode) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Invalid confirmation code or email")
                    .build();
        }

        if (user.getEnabled()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Email is already confirmed")
                    .build();
        }

        user.setEnabled(true);
        userRepository.save(user);

        return ResponseMessage.builder()
                .success(true)
                .message("Email confirmed successfully")
                .build();
    }

    private void sendVerificationCode(String toEmail, String code) {
        String subject = "Tasdiqlash kodingiz";
        String body = "Hurmatli foydalanuvchi,\n\nSizning tasdiqlash kodingiz: " + code +
                "\n\nIltimos, bu kodni hech kimga bermang. Yoki quyidagi link orqali tasdiqlang:\n" +
                "http://localhost:8080/auth/confirm?code=" + code + "&email=" + toEmail + "\n\n" +
                "Hurmat bilan,\nSizning tizimingiz";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("husanchoriyev20040406@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private static String generateCode() {
        StringBuilder confCode = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < 6; j++) {
            confCode.append(random.nextInt(10));
        }
        return confCode.toString();
    }
}
