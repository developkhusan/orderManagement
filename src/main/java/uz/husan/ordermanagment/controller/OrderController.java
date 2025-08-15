package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;

@RequestMapping("/order")
public interface OrderController {
    @GetMapping("/get-list")
    ResponseEntity<?> getAllOrders(@RequestParam Integer page, @RequestParam Integer size,@RequestParam Long chickenId);
    @PostMapping("/create")
    ResponseEntity<?> createOrder(@RequestBody OrderItemAddDTO orderAddDTO);
    @PutMapping("/update")
    ResponseEntity<?> updateOrder(@RequestParam Long mealId,@RequestBody OrderItemAddDTO orderAddDTO);
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteOrder(@RequestParam Long id);
    @GetMapping("/buy")
    ResponseEntity<?> buyOrder();

}
