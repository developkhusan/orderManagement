package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.meal.MealShowDTO;
import uz.husan.ordermanagment.dto.orderItem.BasketShowDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderIteamShowDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderItemAddDTO;
import uz.husan.ordermanagment.dto.orderItem.OrderShowDTO;
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
        orderIteamShowDTO.setOrderId(orderItem.getOrder().getId());

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

        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(), OrderStatus.DEFAULT);
        Optional<Meal> byMealId = mealRepository.findById(orderAddDTO.getMealId());

        if(byMealId.isEmpty()){
            return ResponseMessage.builder().success(false).message("Meal not found").data(null).build();
        }

        Order order;
        if(byClientId.isEmpty()){
            order = new Order();
            order.setClient(user);
            order.setStatus(OrderStatus.DEFAULT);
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
        order.setTotalAmount(order.getTotalAmount().add(orderItem.getTotalPrice()));
        OrderItem save = orderItemRepository.save(orderItem);


        orderRepository.save(order);

        return ResponseMessage.builder()
                .success(true)
                .message("OrderItem created successfully")
                .data(getOrderItem(save))
                .build();

        //  OrderItem orderItem = new OrderItem();

    }


    @Override
    public ResponseMessage buyOrder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(),OrderStatus.DEFAULT);

        if(byClientId.isEmpty()){
            return ResponseMessage.builder()
                    .success(false)
                    .message("Your order is empty")
                    .data(null)
                    .build();
        }
        Order order = byClientId.get();
        if(user.getBalance().compareTo(order.getTotalAmount())<0){
            return ResponseMessage.builder()
                    .success(false)
                    .message("Not enough balance")
                    .data(null)
                    .build();
        }
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);



        return ResponseMessage.builder()
                .success(true)
                .message("Wait for the operator's response")
                .data(byClientId.get())
                .build();

    }

    @Override
    public ResponseMessage getOrderHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Faqat tarix bo‘lishi mumkin bo‘lgan statuslar
        List<OrderStatus> historyStatuses = List.of(
                OrderStatus.ACCEPTED,
                OrderStatus.CLIENT_CANCELLED
        );

        List<Order> orders = orderRepository.findAllByClientIdAndStatusIn(user.getId(), historyStatuses);

        if (orders.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You don't have any order history")
                    .data(null)
                    .build();
        }

        return ResponseMessage.builder()
                .success(true)
                .message("Order history fetched successfully")
                .data(orders)
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
            if(!user.getId().equals(order.getClient().getId())){
                return ResponseMessage.builder().success(false).message("You cannot confirm your own order").data(null).build();
            }
            order.setStatus(OrderStatus.ACCEPTED);
            order.setDeliveryDate(LocalDateTime.now());
            user.setBalance(user.getBalance().subtract(order.getTotalAmount())); // balansidan buyurtma summasini chiqarish
            order.getDeliverer().setBalance(order.getDeliverer().getBalance().add(order.getTotalAmount()));
            /// pulni kimga tashlay
            orderRepository.save(order);
            userRepository.save(user);

            return ResponseMessage.builder().success(true).message("Order confirmed successfully").data(byId.get()).build();

        }
        return ResponseMessage.builder().success(false).message(" Delivered order not found").data(null).build();

    }


    @Override
    public ResponseMessage showCurrentBasket() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(), OrderStatus.DEFAULT);

        if (byClientId.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Your basket is empty")
                    .data(null)
                    .build();
        }

        Order order = byClientId.get();

        // orderItem larni DTO ga map qilish
        List<OrderIteamShowDTO> items = order.getOrderItems().stream()
                .map(this::getOrderItem)
                .toList();

        BasketShowDTO dto = new BasketShowDTO(
                order.getId(),
                order.getTotalAmount(),
                items
        );

        return ResponseMessage.builder()
                .success(true)
                .message("Your current basket")
                .data(dto)
                .build();
    }

    @Override
    public ResponseMessage cancelBasket() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> byClientId = orderRepository.findByClientIdAndStatus(user.getId(), OrderStatus.PENDING);

        if (byClientId.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You don't have any pending basket to cancel")
                    .data(null)
                    .build();
        }

        Order order = byClientId.get();
        order.setStatus(OrderStatus.CLIENT_CANCELLED);
        order.setDeliveryDate(LocalDateTime.now()); // bekor qilingan vaqtni belgilab qo‘yish mumkin
        orderRepository.save(order);

        return ResponseMessage.builder()
                .success(true)
                .message("Basket cancelled successfully")
                .data(order.getId())
                .build();
    }
    @Override
    public ResponseMessage deleteOrderItem(Long orderItemId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Order> activeBasketOpt = orderRepository.findByClientIdAndStatus(user.getId(), OrderStatus.DEFAULT);

        if (activeBasketOpt.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Active basket not found")
                    .build();
        }

        Order activeBasket = activeBasketOpt.get();

        Optional<OrderItem> orderItemOpt = activeBasket.getOrderItems()
                .stream()
                .filter(item -> item.getId().equals(orderItemId))
                .findFirst();

        if (orderItemOpt.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Item not found in your basket")
                    .build();
        }

        OrderItem orderItem = orderItemOpt.get();

        activeBasket.setTotalAmount(
                activeBasket.getTotalAmount().subtract(orderItem.getTotalPrice())
        );

        activeBasket.getOrderItems().remove(orderItem);
        orderItemRepository.delete(orderItem);
        orderRepository.save(activeBasket);

        return ResponseMessage.builder()
                .success(true)
                .message("Item deleted successfully")
                .data(activeBasket.getId())
                .build();
    }


    @Override
    public ResponseMessage updateOrderItem(Long orderItemId, Integer newQuantity) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Order> activeOrderOpt = orderRepository.findByClientIdAndStatus(user.getId(), OrderStatus.DEFAULT);

        if (activeOrderOpt.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Active basket not found")
                    .build();
        }

        Order activeOrder = activeOrderOpt.get();
        Optional<OrderItem> orderItemOpt = orderItemRepository.findById(orderItemId);

        if (orderItemOpt.isEmpty() || !orderItemOpt.get().getOrder().getId().equals(activeOrder.getId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Order item not found in your active basket")
                    .build();
        }

        OrderItem orderItem = orderItemOpt.get();

        if (newQuantity <= 0) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Quantity must be greater than zero")
                    .build();
        }

        activeOrder.setTotalAmount(activeOrder.getTotalAmount().subtract(orderItem.getTotalPrice()));

        orderItem.setQuantity(newQuantity);
        orderItem.setTotalPrice(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(newQuantity)));

        activeOrder.setTotalAmount(activeOrder.getTotalAmount().add(orderItem.getTotalPrice()));

        orderItemRepository.save(orderItem);
        orderRepository.save(activeOrder);

        return ResponseMessage.builder()
                .success(true)
                .message("Item updated successfully")
                .data(orderItem)
                .build();
    }

    @Override
    public ResponseMessage getActiveOrdersForConfirmation() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // faqat PENDING, DELIVERED, ACCEPTED statuslarni olib kelamiz
        List<OrderStatus> statuses = List.of(OrderStatus.PENDING, OrderStatus.DELIVERED, OrderStatus.CONFIRMED, OrderStatus.SENT,OrderStatus.READY);

        List<Order> orders = orderRepository.findAllByClientIdAndStatusIn(user.getId(), statuses);

        if (orders.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You don't have any active orders to confirm")
                    .data(null)
                    .build();
        }

        List<OrderShowDTO> orderDTOs = orders.stream()
                .map(order -> new OrderShowDTO(
                        order.getId(),
                        order.getClient().getFullName(),
                        order.getStatus(),
                        order.getTotalAmount(),
                        order.getDeliveryDate()
                ))
                .toList();

        return ResponseMessage.builder()
                .success(true)
                .message("Active orders for confirmation")
                .data(orderDTOs)
                .build();
    }


}
