package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.config.SecurityConfig;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.UserService;

import java.math.BigDecimal;
@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public ResponseMessage getUserData() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        return ResponseMessage.builder()
                .success(true)
                .message("User data fetched successfully")
                .data(user)
                .build();
    }

    @Override
    public ResponseMessage fillBalance(BigDecimal amount) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Amount must be greater than zero")
                    .data(null)
                    .build();
        }

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
        return ResponseMessage.builder()
                .success(true)
                .message("Balance updated successfully")
                .data(user)
                .build();
    }

    @Override
    public ResponseMessage editUserInfo(UserRequestDTO userRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (userRequestDTO == null) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User request data is null")
                    .data(null)
                    .build();
        }

        if (userRequestDTO.getFullName() != null) {
            user.setFullName(userRequestDTO.getFullName());
        }
        if (userRequestDTO.getEmail() != null) {
            user.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        if (userRequestDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        }
        if (userRequestDTO.getUserLocation() != null) {
            user.setUserLocation(userRequestDTO.getUserLocation());
        }
        if (userRequestDTO.getImageUrl() != null) {
            user.setImageUrl(userRequestDTO.getImageUrl());
        }
        userRepository.save(user);
        return ResponseMessage.builder()
                .success(true)
                .message("User information updated successfully")
                .data(user)
                .build();
    }
}
