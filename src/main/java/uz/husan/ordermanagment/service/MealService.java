package uz.husan.ordermanagment.service;

import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;

@Service
public interface MealService {
    ResponseMessage getAllMeals(Integer page, Integer size);

    ResponseMessage createMeal( MealAddDTO mealAddDTO);

    ResponseMessage updateMeal(Long id,  MealAddDTO productDTO);

    ResponseMessage deleteMeal(Long id);
}
