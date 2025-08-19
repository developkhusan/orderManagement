package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.orders.OrdersShowDTO;
import uz.husan.ordermanagment.entity.Chicken;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.OrderStatus;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.ChickenRepository;
import uz.husan.ordermanagment.repository.OrderRepository;
import uz.husan.ordermanagment.service.DelivererService;
import java.util.Optional;

@RequiredArgsConstructor
@Service("DelivererService")
public class DelivererServiceImpl implements DelivererService {
    private final OrderRepository orderRepository;
    private final ChickenRepository chickenRepository;

    public OrdersShowDTO getOrders(Order order) {
        OrdersShowDTO ordersShowDTO = new OrdersShowDTO();
        ordersShowDTO.setId(order.getId());
        ordersShowDTO.setClientName(order.getClient().getFullName());
        ordersShowDTO.setStatus(order.getStatus());
        ordersShowDTO.setLocation(order.getClient().getUserLocation());
        ordersShowDTO.setTotalAmount(order.getTotalAmount());
        ordersShowDTO.setOrderDateTime(order.getOrderDateTime().toString());
        return ordersShowDTO;
    }
    @Override
    public ResponseMessage getAllOrders(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Order> all = orderRepository.findByStatus(OrderStatus.READY,pageRequest);
        if (all.isEmpty()){
            return ResponseMessage.builder().success(false).message("Order no such exists").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("meals fetched successfully")
                .success(true)
                .data(all)
                .build();

    }

    @Override
    public ResponseMessage deliveredOrder(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRole().equals(Role.DELIVERY)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to deliver orders")
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
        if(!order.getStatus().equals(OrderStatus.SENT)){
            return ResponseMessage.builder()
                    .success(false)
                    .message("Order is not ready for delivery")
                    .data(null)
                    .build();
        }
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        return ResponseMessage.builder()
                .success(true)
                .message("Order delivered successfully")
                .data(order)
                .build();
    }

    @Override
    public ResponseMessage takeToDeliver(Long orderId) {
        User deliverer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!deliverer.getRole().equals(Role.DELIVERY)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to take orders for delivery")
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
        if(!order.getStatus().equals(OrderStatus.READY)){
            return ResponseMessage.builder()
                    .success(false)
                    .message("Order is not ready for delivery")
                    .data(null)
                    .build();
        }
        Chicken chicken = order.getOrderItems().stream()
                .findFirst()
                .map(orderItem -> orderItem.getMeal().getChicken())
                .orElse(null);


        chicken.setBalance(chicken.getBalance().add(order.getTotalAmount()));
        if (deliverer.getBalance().compareTo(order.getTotalAmount()) < 0) {
            return ResponseMessage.builder().success(false).message("Deliverer not enough").data(null).build();
        }
        if (order.getStatus() != OrderStatus.READY) {
            return ResponseMessage.builder().success(false).message("Order is not in Ready status").data(null).build();
        }
        deliverer.setBalance(deliverer.getBalance().subtract(order.getTotalAmount()));
        chicken.setBalance(chicken.getBalance().add(order.getTotalAmount()));
        chickenRepository.save(chicken);
        deliverer.setBusy(true);
        order.setStatus(OrderStatus.SENT);
        order.setDeliverer(deliverer);
        orderRepository.save(order);
        return ResponseMessage.builder().success(true).message("Order confirmed successfully").data(getOrders(order)).build();
    }

}
