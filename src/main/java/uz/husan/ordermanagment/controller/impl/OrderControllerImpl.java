package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.OrderController;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.OrderIteamService;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderIteamService orderIteamService;


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
    public ResponseEntity<?> ordersDelivered() {
        ResponseMessage responseMessage = orderIteamService.getOrderHistory();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> orderConfirmation(Long orderId) {
        ResponseMessage responseMessage = orderIteamService.orderConfirmation(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> currentBasket() {
       ResponseMessage responseMessage = orderIteamService.showCurrentBasket();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> cancelBasket(Long orderId) {
        ResponseMessage responseMessage = orderIteamService.cancelBasket(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> deleteBasketItem(Long orderItemId) {
        ResponseMessage responseMessage = orderIteamService.deleteOrderItem(orderItemId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> updateBasketItemQuantity(Long orderItemId, Integer quantity) {
        ResponseMessage responseMessage = orderIteamService.updateOrderItem(orderItemId, quantity);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> getActiveOrdersForConfirmation() {
        ResponseMessage responseMessage = orderIteamService.getActiveOrdersForConfirmation();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> buyOrder() {
        ResponseMessage responseMessage = orderIteamService.buyOrder();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}