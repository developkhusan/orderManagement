package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/chef")
public interface ChefController{
    @GetMapping("/orders")
    ResponseEntity<?> getAllOrders(@RequestParam Integer page, @RequestParam Integer size);
    @PutMapping("/confirm-order")
    ResponseEntity<?> confirmOrder(@RequestParam Long orderId);
    @PutMapping("/preparation-order")
    ResponseEntity<?> preparationOrder(@RequestParam Long orderId);
    @PutMapping("/ready-order")
    ResponseEntity<?> readyOrder(@RequestParam Long orderId);

}
