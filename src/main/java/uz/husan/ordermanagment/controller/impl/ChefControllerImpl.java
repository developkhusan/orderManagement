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
    public ResponseEntity<?> confirmOrder(Long orderId) {
        ResponseMessage responseMessage = chefService.confirmOrder(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> preparationOrder(Long orderId) {
        ResponseMessage responseMessage = chefService.preparationOrder(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> readyOrder(Long orderId) {
        ResponseMessage responseMessage = chefService.readyOrder(orderId);
        return ResponseEntity.status(responseMessage.getSuccess() ? 200 : 400).body(responseMessage);
    }


}
