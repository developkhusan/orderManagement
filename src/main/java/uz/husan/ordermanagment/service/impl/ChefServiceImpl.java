package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.orders.OrdersShowDTO;
import uz.husan.ordermanagment.entity.Chicken;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.OrderStatus;
import uz.husan.ordermanagment.entity.enums.Role;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.ChickenRepository;
import uz.husan.ordermanagment.repository.OrderItemRepository;
import uz.husan.ordermanagment.repository.OrderRepository;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.ChefService;

import java.util.Optional;
@RequiredArgsConstructor
@Service("chefService")
public class ChefServiceImpl implements ChefService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ChickenRepository chickenRepository;
    private final OrderItemRepository orderItemRepository;

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
        Page<OrdersShowDTO> all = orderRepository.findByStatus(OrderStatus.READY,pageRequest).map(this::getOrders );
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
    public ResponseMessage confirmOrder(Long orderId, Long deliverId) {
        Optional<User> delivererOptional = userRepository.findById(deliverId);
        if (delivererOptional.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Deliverer not found").data(null).build();
        }
        if(delivererOptional.get().getBusy()) {
            return ResponseMessage.builder().success(false).message("Deliverer is busy").data(null).build();
        }
        User deliverer = delivererOptional.get();
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Order not found").data(null).build();
        }
        Order order = orderOptional.get();
        Chicken chicken = order.getOrderItems().stream()
                .findFirst()
                .map(orderItem -> orderItem.getMeal().getChicken())
                .orElse(null);


        System.out.println(chicken);
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
        System.out.println(chicken.getChickenName());
        deliverer.setBusy(true);
        order.setStatus(OrderStatus.SENT);
        order.setDeliverer(deliverer);
        orderRepository.save(order);
        return ResponseMessage.builder().success(true).message("Order confirmed successfully").data(getOrders(order)).build();
    }

    @Override
    public ResponseMessage getAllDeliverers(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> all = userRepository.findByRole(Role.DELIVERY, pageRequest);
        if (all.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Deliverers not found").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("Deliverers fetched successfully")
                .success(true)
                .data(all)
                .build();
    }

}
