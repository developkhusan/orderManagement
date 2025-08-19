package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;

import java.math.BigDecimal;

@RequestMapping("/user")
public interface UserController {
    @GetMapping("/data")
    ResponseEntity<?> getUserData();

    @PutMapping("/fillBalance")
    ResponseEntity<?> fillBalance(@RequestParam BigDecimal amount);

    @PutMapping("/user/editUserInfo")
    ResponseEntity<?> editUserInfo(@RequestBody UserRequestDTO userRequestDTO);

}
