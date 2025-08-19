package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.ChefController;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.ChefService;

@RestController
@RequiredArgsConstructor
public class ChefControllerImpl implements ChefController {
    private final ChefService chefService;

    @Override
    public ResponseEntity<?> getAllOrders(Integer page, Integer size) {
        ResponseMessage responseMessage = chefService.getAllOrders(page, size);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> getAllDeliverers(Integer page, Integer size) {
        ResponseMessage responseMessage = chefService.getAllDeliverers(page, size);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> confirmOrder(Long orderId, Long deliverId) {
        ResponseMessage responseMessage = chefService.confirmOrder(orderId, deliverId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
