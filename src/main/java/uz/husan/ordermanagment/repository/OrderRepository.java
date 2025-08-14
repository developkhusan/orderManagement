package uz.husan.ordermanagment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);


    Optional<Order> findById(Long integer);
}
