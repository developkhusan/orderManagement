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
        Page<OrdersShowDTO> all = orderRepository.findByStatus(OrderStatus.PENDING,pageRequest).map(this::getOrders );
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
    public ResponseMessage confirmOrder(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRole().equals(Role.CHEF)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to confirm orders")
                    .data(null)
                    .build();
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Order not found").data(null).build();
        }
        Order order = orderOptional.get();
        if (order.getStatus() == OrderStatus.READY) {
            return ResponseMessage.builder().success(false).message("Order is already ready").data(getOrders(order)).build();
        }
        if (order.getStatus() == OrderStatus.SENT) {
            return ResponseMessage.builder().success(false).message("Order is already sent").data(getOrders(order)).build();
        }
        if (order.getStatus() == OrderStatus.PREPARATION) {
            return ResponseMessage.builder().success(false).message("Order is already in preparation").data(getOrders(order)).build();
        }
        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
            return ResponseMessage.builder().success(true).message("Order confirmed successfully").data(getOrders(order)).build();
        }
        return ResponseMessage.builder().success(false).message("Order status is not valid for confirmation").data(getOrders(order)).build();
    }

    @Override
    public ResponseMessage preparationOrder(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRole().equals(Role.CHEF)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to prepare orders")
                    .data(null)
                    .build();
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Order not found").data(null).build();
        }
        Order order = orderOptional.get();
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            return ResponseMessage.builder().success(false).message("Order is not in Confirmed status").data(getOrders(order)).build();
        }
        order.setStatus(OrderStatus.PREPARATION);
        orderRepository.save(order);
        return ResponseMessage.builder().success(true).message("Order is now in preparation").data(getOrders(order)).build();
    }

    @Override
    public ResponseMessage readyOrder(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRole().equals(Role.CHEF)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You are not authorized to mark orders as ready")
                    .data(null)
                    .build();
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseMessage.builder().success(false).message("Order not found").data(null).build();
        }
        Order order = orderOptional.get();
        if (order.getStatus() != OrderStatus.PREPARATION) {
            return ResponseMessage.builder().success(false).message("Order is not in Preparation status").data(getOrders(order)).build();
        }
        order.setStatus(OrderStatus.READY);
        orderRepository.save(order);
        return ResponseMessage.builder().success(true).message("Order is now ready").data(getOrders(order)).build();
    }


}
