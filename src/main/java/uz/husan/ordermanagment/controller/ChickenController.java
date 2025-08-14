package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.chicken.ChickenAddDTO;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;

@RequestMapping("/chicken")
public interface ChickenController {
        @GetMapping
        ResponseEntity<?> getAllChickens(@RequestParam Integer page, @RequestParam Integer size);
        @PostMapping
        ResponseEntity<?> createChicken(@RequestBody ChickenAddDTO chickenAddDTO);
        @PutMapping
        ResponseEntity<?> updateChicken(@RequestParam Long chickenId,@RequestBody ChickenAddDTO chickenAddDTO);
        @DeleteMapping
        ResponseEntity<?> deleteChicken(@RequestParam Long id);
}
