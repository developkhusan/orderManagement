package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
