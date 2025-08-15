package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.OrderController;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.OrderIteamService;
@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderIteamService  orderIteamService;


    @Override
    public ResponseEntity<?> getAllOrders(Integer page, Integer size, Long chickenId) {
        ResponseMessage responseMessage = orderIteamService.getAllOrders(page, size, chickenId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> createOrder(OrderItemAddDTO orderAddDTO) {
        ResponseMessage responseMessage = orderIteamService.createOrder(orderAddDTO);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> updateOrder(Long orderId, OrderItemAddDTO orderAddDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteOrder(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> buyOrder() {
        ResponseMessage responseMessage = orderIteamService.buyOrder();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
