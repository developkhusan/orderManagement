package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("/deliverer")
public interface DelivererController {
    @GetMapping("/my-order")
    ResponseEntity<?> getAllMyOrders();
    @PutMapping("/my-shipped-deliver")
    ResponseEntity<?> orderDeliver(@RequestParam Long orderId, @RequestParam Long deliverId);

}
