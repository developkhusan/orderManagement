package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;

@RequestMapping("/order")
public interface OrderController {
    @GetMapping("/restaurant-meal-list")
    ResponseEntity<?> getAllOrders(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Long chickenId);

    @PostMapping("/give-orders")
    ResponseEntity<?> createOrder(@RequestBody OrderItemAddDTO orderAddDTO);


    @GetMapping("/basket-buy")
    ResponseEntity<?> buyOrder();

    @GetMapping("/my-history")
    ResponseEntity<?> ordersDelivered();

    @GetMapping("/confirmation-order")
    ResponseEntity<?> orderConfirmation(@RequestParam Long orderId);

    @GetMapping("/my-current-basket")
    ResponseEntity<?> currentBasket();

    @PostMapping("/cancel-basket")
    ResponseEntity<?> cancelBasket(@RequestParam Long orderId);

    @DeleteMapping("/basket-item")
    ResponseEntity<?> deleteBasketItem(@RequestParam Long orderItemId);

    @PutMapping("/basket-iteam-quantity")
    ResponseEntity<?> updateBasketItemQuantity(@RequestParam Long orderItemId, @RequestParam Integer quantity);

    @GetMapping("/my-active-orders")
    ResponseEntity<?> getActiveOrdersForConfirmation();




}
