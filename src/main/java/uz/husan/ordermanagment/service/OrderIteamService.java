package uz.husan.ordermanagment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
@Service
public interface OrderIteamService {
    ResponseMessage getAllOrders(Integer page, Integer size,Long chickenId);

    ResponseMessage createOrder( OrderItemAddDTO orderAddDTO);

    ResponseMessage updateOrder(Long id,  OrderItemAddDTO productDTO);

    ResponseMessage deleteOrder(Long id);
    ResponseMessage buyOrder();

    ResponseMessage ordersDelivered();
    ResponseMessage orderConfirmation(Long orderId);
}
