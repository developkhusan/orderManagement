package uz.husan.ordermanagment.service;

import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;

@Service
public interface OrderIteamService {
    ResponseMessage getAllOrders(Integer page, Integer size, Long chickenId);

    ResponseMessage createOrder( OrderItemAddDTO orderAddDTO);


    ResponseMessage buyOrder();

    ResponseMessage getOrderHistory();

    ResponseMessage orderConfirmation(Long orderId);

    ResponseMessage showCurrentBasket();

    ResponseMessage cancelBasket(Long orderId);

    ResponseMessage deleteOrderItem(Long orderItemId);

     ResponseMessage updateOrderItem(Long orderItemId, Integer newQuantity);
     ResponseMessage getActiveOrdersForConfirmation();
}
