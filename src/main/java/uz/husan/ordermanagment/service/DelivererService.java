package uz.husan.ordermanagment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.husan.ordermanagment.message.ResponseMessage;

@Service
public interface DelivererService {
    ResponseMessage getAllMyOrders();
    ResponseMessage orderDeliver( Long orderId,  Long deliverId);
}
