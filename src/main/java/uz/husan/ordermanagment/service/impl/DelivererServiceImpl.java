package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.OrderStatus;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.OrderRepository;
import uz.husan.ordermanagment.service.DelivererService;

import java.util.Optional;

@RequiredArgsConstructor
@Service("DelivererService")
public class DelivererServiceImpl implements DelivererService {
    private final OrderRepository orderRepository;

    @Override
    public ResponseMessage getAllMyOrders() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRole().equals(Role.DELIVERY)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to view deliverer order")
                    .data(null)
                    .build();
        }
        Optional<Order> byClientIdAndStatus = orderRepository.findByDelivererIdAndStatus(user.getId(), OrderStatus.SENT);
        if (byClientIdAndStatus.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No order found for the deliverer")
                    .data(null)
                    .build();
        }
        return ResponseMessage.builder()
                .success(true)
                .message("Orders fetched successfully")
                .data(byClientIdAndStatus.get())
                .build();

    }

    @Override
    public ResponseMessage orderDeliver(Long orderId, Long deliverId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(deliverId)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to deliver this order")
                    .data(null)
                    .build();
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Order not found")
                    .data(null)
                    .build();
        }

        Order order = orderOptional.get();
        if (!order.getStatus().equals(OrderStatus.SENT)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Order is not in a deliverable state")
                    .data(null)
                    .build();
        }
       if(!order.getDeliverer().equals(deliverId)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("This delivery is not you.")
                    .data(null)
                    .build();
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliverer(user);
        orderRepository.save(order);


        return ResponseMessage.builder()
                .success(true)
                .message("Order delivered successfully")
                .data(order)
                .build();
    }
}
