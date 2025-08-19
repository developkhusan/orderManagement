package uz.husan.ordermanagment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.husan.ordermanagment.message.ResponseMessage;

@Service
public interface ChefService {
    ResponseMessage getAllOrders(Integer page,  Integer size);
    ResponseMessage confirmOrder(Long orderId);
    ResponseMessage preparationOrder(Long orderId);
    ResponseMessage readyOrder(Long orderId);
}
