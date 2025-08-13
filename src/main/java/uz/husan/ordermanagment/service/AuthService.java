package uz.husan.ordermanagment.service;

import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.RegisterDto;
import uz.husan.ordermanagment.message.ResponseMessage;

public interface AuthService {
    ResponseMessage signup(RegisterDto registerDto);
    ResponseMessage signin(EmailAndPasswordDTO emailAndPasswordDTO);
    ResponseMessage confirm(String code, String email);
}