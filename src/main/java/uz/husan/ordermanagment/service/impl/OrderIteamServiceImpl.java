package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.meal.MealShowDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderIteamShowDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.entity.Meal;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.OrderItem;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.OrderStatus;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.MealRepository;
import uz.husan.ordermanagment.repository.OrderItemRepository;
import uz.husan.ordermanagment.repository.OrderRepository;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.OrderIteamService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("OrderService")
public class OrderIteamServiceImpl implements OrderIteamService {
    private final OrderRepository orderRepository;
    private final MealRepository mealRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;


    public OrderIteamShowDTO getOrderItem(OrderItem orderItem) {

        OrderIteamShowDTO orderIteamShowDTO = new OrderIteamShowDTO();
        orderIteamShowDTO.setId(orderItem.getId());
        orderIteamShowDTO.setOrderId(orderItem.getId());

        Optional<Meal> byMealId = mealRepository.findById(orderItem.getMeal().getId());

        orderIteamShowDTO.setMealName(byMealId.get().getName());
        orderIteamShowDTO.setQuantity(orderItem.getQuantity());
        orderIteamShowDTO.setUnitPrice(orderItem.getUnitPrice());
        orderIteamShowDTO.setTotalPrice(orderItem.getTotalPrice());

        return orderIteamShowDTO;

    }

    public MealShowDTO getMeal(Meal meal) {
        MealShowDTO mealShowDTO = new MealShowDTO();
        mealShowDTO.setId(meal.getId());
        mealShowDTO.setName(meal.getName());
        mealShowDTO.setDescription(meal.getDescription());
        mealShowDTO.setPrice(meal.getPrice());
        mealShowDTO.setCategory(meal.getCategory());
        mealShowDTO.setImageUrl(meal.getImageUrl());
        mealShowDTO.setChickenName(meal.getChicken().getChickenName());
        return mealShowDTO;
    }



    @Override
    public ResponseMessage getAllOrders(Integer page, Integer size, Long chickenId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<MealShowDTO> all = mealRepository.findByChickenId(chickenId,pageRequest).map(this::getMeal );
        if (all.isEmpty()){
            return ResponseMessage.builder().success(false).message("Cards no such exists").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("meals fetched successfully")
                .success(true)
                .data(all)
                .build();
    }

    @Override
    public ResponseMessage createOrder(OrderItemAddDTO orderAddDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(),OrderStatus.PENDING);
        Optional<Meal> byMealId = mealRepository.findById(orderAddDTO.getMealId());

     if(byMealId.isEmpty()){
         return ResponseMessage.builder().success(false).message("Meal not found").data(null).build();
     }


        Order order;
        if(byClientId.isEmpty()){
            order = new Order();
            order.setClient(user);
            order.setStatus(OrderStatus.PENDING);
            order.setTotalAmount(BigDecimal.ZERO);
            order.setOrderDateTime(LocalDateTime.now());
            order.setOrderItems(new ArrayList<>());
            order = orderRepository.save(order);
        } else {
            order = byClientId.get(); // faqat else ichida chaqiramiz
        }

         OrderItem orderItem = new OrderItem();
         orderItem.setOrder(order);
         orderItem.setMeal(byMealId.get());
         orderItem.setQuantity(orderAddDTO.getQuantity());
         orderItem.setUnitPrice(byMealId.get().getPrice());
         orderItem.setTotalPrice(
                BigDecimal.valueOf(orderAddDTO.getQuantity()) // sonni BigDecimal ga o‘giradi
                        .multiply(byMealId.get().getPrice())      // price bilan ko‘paytiradi
        );

        orderItem.setCreatedAt(LocalDateTime.now());

        //Orderlarga qoshib qoyish
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(orderItem);

        OrderItem save = orderItemRepository.save(orderItem);

        order.setTotalAmount(order.getTotalAmount().add(orderItem.getMeal().getPrice()));

        orderRepository.save(order);

        return ResponseMessage.builder()
                .success(true)
                .message("OrderItem created successfully")
                .data(getOrderItem(save))
                .build();

      //  OrderItem orderItem = new OrderItem();

    }

    @Override
    public ResponseMessage updateOrder(Long id, OrderItemAddDTO productDTO) {
        return null;
    }

    @Override
    public ResponseMessage deleteOrder(Long id) {
        return null;
    }

    @Override
    public ResponseMessage buyOrder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(),OrderStatus.PENDING);

        if(byClientId.isEmpty()){
            return ResponseMessage.builder()
                    .success(false)
                    .message("Your order is empty")
                    .data(null)
                    .build();
        }
        Order order = byClientId.get();
        order.setStatus(OrderStatus.READY);
        orderRepository.save(order);



        return ResponseMessage.builder()
                .success(true)
                .message("Wait for the operator's response")
                .data(byClientId.get())
                .build();

    }

    @Override
    public ResponseMessage ordersDelivered() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> allClientIdAndStatus = orderRepository.findAllClientIdAndStatus(user.getId(), OrderStatus.DELIVERED);

if (allClientIdAndStatus.isEmpty()){
    return ResponseMessage.builder().success(false).message("Cards no such exists").data(null).build();

}
        return ResponseMessage
                .builder()
                .message("All orders delivered successfully")
                .success(true)
                .data(allClientIdAndStatus)
                .build();

    }

    @Override
    public ResponseMessage orderConfirmation(Long orderId) {
        Optional<Order> byId = orderRepository.findById(orderId);
        if (byId.isEmpty()){
            return ResponseMessage.builder().success(false).message("Order not found").data(null).build();
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (byId.get().getStatus().equals(OrderStatus.DELIVERED)){
            Order order = byId.get();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            user.setBalance(user.getBalance()-byId.get().getTotalAmount().doubleValue());

            /// pulni kimga tashlay

            userRepository.save(user);

            return ResponseMessage.builder().success(true).message("Order confirmed successfully").data(byId.get()).build();

        }
       return ResponseMessage.builder().success(false).message(" Delivered order not found").data(null).build();

    }
}
