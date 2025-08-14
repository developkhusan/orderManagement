package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
@RequestMapping("/meal")
public interface MealController {
    @GetMapping("/get-list")
    ResponseEntity<?> getAllMeals(@RequestParam Integer page, @RequestParam Integer size);
    @PostMapping
    ResponseEntity<?> createMeal(@RequestBody MealAddDTO mealAddDTO);
    @PutMapping
    ResponseEntity<?> updateMeal(@RequestParam Long mealId,@RequestBody MealAddDTO mealAddDTO);
    @DeleteMapping
    ResponseEntity<?> deleteMeal(@RequestParam Long id);
}
