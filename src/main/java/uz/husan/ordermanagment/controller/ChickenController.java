package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.chicken.ChickenAddDTO;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;

@RequestMapping("/chicken")
public interface ChickenController {
        @GetMapping("/users")
        ResponseEntity<?> getAllUsers(@RequestParam Integer page, @RequestParam Integer size);
        @GetMapping("/all")
        ResponseEntity<?> getAllChickens(@RequestParam Integer page, @RequestParam Integer size);
        @PostMapping("/create")
        ResponseEntity<?> createChicken(@RequestBody ChickenAddDTO chickenAddDTO);
        @PutMapping("/update")
        ResponseEntity<?> updateChicken(@RequestParam Long chickenId,@RequestBody ChickenAddDTO chickenAddDTO);
        @DeleteMapping("/delete")
        ResponseEntity<?> deleteChicken(@RequestParam Long id);
}
