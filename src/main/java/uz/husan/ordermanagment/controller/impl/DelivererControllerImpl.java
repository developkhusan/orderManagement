package uz.husan.ordermanagment.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.husan.ordermanagment.controller.DelivererController;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.service.DelivererService;

@RestController
@RequiredArgsConstructor
public class DelivererControllerImpl implements DelivererController {
    private final DelivererService delivererService;

    @Override
    public ResponseEntity<?> getAllMyOrders() {
        ResponseMessage responseMessage = delivererService.getAllMyOrders();
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> orderDeliver(Long orderId, Long deliverId) {
        ResponseMessage responseMessage = delivererService.orderDeliver(orderId, deliverId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }
}
