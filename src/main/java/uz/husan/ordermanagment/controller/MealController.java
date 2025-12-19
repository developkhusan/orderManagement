package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
@RequestMapping("/meal")
public interface MealController {
    @GetMapping("/get-list")
    ResponseEntity<?> getAllMeals(@RequestParam (defaultValue = "1")Integer page, @RequestParam (defaultValue = "10")Integer size);
    @PostMapping("/create")
    ResponseEntity<?> createMeal(@RequestBody MealAddDTO mealAddDTO);
    @PutMapping("/update")
    ResponseEntity<?> updateMeal(@RequestParam Long mealId,@RequestBody MealAddDTO mealAddDTO);
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteMeal(@RequestParam Long id);
}
