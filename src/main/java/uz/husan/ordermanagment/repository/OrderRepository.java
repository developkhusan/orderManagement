package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
