package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.ChickenController;
import uz.husan.ordermanagment.dto.chicken.ChickenAddDTO;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.ChickenService;

@RestController
@RequiredArgsConstructor
public class ChickenControllerImpl implements ChickenController {
    private final ChickenService chickenService;
    @Override
    public ResponseEntity<?> getAllChickens(Integer page, Integer size) {
        ResponseMessage responseMessage = chickenService.getAllChickens(page, size);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);

    }

    @Override
    public ResponseEntity<?> createChicken(ChickenAddDTO chickenAddDTO) {
        ResponseMessage responseMessage = chickenService.createChicken(chickenAddDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> updateChicken(Long chickenId, ChickenAddDTO chickenAddDTO) {
        ResponseMessage responseMessage = chickenService.updateChicken(chickenId, chickenAddDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> deleteChicken(Long id) {
        ResponseMessage responseMessage = chickenService.deleteChicken(id);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
