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
    public ResponseEntity<?> getAllOrders(Integer page, Integer size) {
        ResponseMessage responseMessage = delivererService.getAllOrders(page, size);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> deliveredOrder(Long orderId) {
        ResponseMessage responseMessage = delivererService.deliveredOrder(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> takeToDeliver(Long orderId) {
        ResponseMessage responseMessage = delivererService.takeToDeliver(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }


}
