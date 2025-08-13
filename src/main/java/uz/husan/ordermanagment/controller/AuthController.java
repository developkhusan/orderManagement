package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.RegisterDto;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;

@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody RegisterDto registerDto);

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody EmailAndPasswordDTO emailAndPasswordDTO);

    @GetMapping("/confirm")
    ResponseEntity<?> confirm(@RequestParam String code, @RequestParam String email);
}
