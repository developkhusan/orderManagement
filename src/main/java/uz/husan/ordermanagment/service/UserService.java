package uz.husan.ordermanagment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;
import uz.husan.ordermanagment.message.ResponseMessage;

import java.math.BigDecimal;
@Service
public interface UserService {

    ResponseMessage getUserData();

    ResponseMessage fillBalance( BigDecimal amount);

    ResponseMessage editUserInfo( UserRequestDTO userRequestDTO);

}
