package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.MealController;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.ChickenService;
import uz.husan.ordermanagment.service.MealService;
@RestController
@RequiredArgsConstructor
public class MealControllerImpl implements MealController {
    private final MealService mealService;

    @Override
    public ResponseEntity<?> getAllMeals(Integer page, Integer size) {
        ResponseMessage responseMessage = mealService.getAllMeals(page, size);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> createMeal(MealAddDTO mealAddDTO) {
        ResponseMessage responseMessage = mealService.createMeal(mealAddDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> updateMeal(Long mealId, MealAddDTO mealAddDTO) {
        ResponseMessage responseMessage = mealService.updateMeal(mealId, mealAddDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> deleteMeal(Long id) {
        ResponseMessage responseMessage = mealService.deleteMeal(id);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
