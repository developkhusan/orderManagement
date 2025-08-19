package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.UserController;
import uz.husan.ordermanagment.dto.auth.UserRequestDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.UserService;

import java.math.BigDecimal;
@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<?> getUserData() {
        ResponseMessage responseMessage =userService.getUserData();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> fillBalance(BigDecimal amount) {
        ResponseMessage responseMessage = userService.fillBalance(amount);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> editUserInfo(UserRequestDTO userRequestDTO) {
        ResponseMessage responseMessage = userService.editUserInfo(userRequestDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
