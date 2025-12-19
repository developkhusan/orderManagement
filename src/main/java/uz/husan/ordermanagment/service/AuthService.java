package uz.husan.ordermanagment.service;

import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.auth.EmailAndPasswordDTO;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;
import uz.husan.ordermanagment.message.ResponseMessage;


@Service
public interface AuthService {
    ResponseMessage signup(UserRequestDTO userRequestDTO);
    ResponseMessage signin(EmailAndPasswordDTO emailAndPasswordDTO);
    ResponseMessage confirm(String code, String email);


}
