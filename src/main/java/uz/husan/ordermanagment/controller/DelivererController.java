package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("/deliverer")
public interface DelivererController {
    @GetMapping("/all-ready-order")
    ResponseEntity<?> getAllOrders(@RequestParam Integer page, @RequestParam Integer size);
    @PutMapping("/delivered-order")
    ResponseEntity<?> deliveredOrder(@RequestParam Long orderId);
    @PutMapping("/take-to-deliver")
    ResponseEntity<?> takeToDeliver(@RequestParam Long orderId);

}
