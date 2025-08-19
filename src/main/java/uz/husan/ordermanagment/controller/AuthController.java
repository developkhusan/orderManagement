package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;

import java.math.BigDecimal;

@RequestMapping("/auth")
public interface AuthController {

     @PostMapping("/signup")
     ResponseEntity<?> signup(@RequestBody UserRequestDTO userRequestDTO);

     @PostMapping("/signin")
     ResponseEntity<?> signin(@RequestBody EmailAndPasswordDTO emailAndPasswordDTO);

     @GetMapping("/confirm")
     ResponseEntity<?> confirm(@RequestParam String code,@RequestParam String email);

}
