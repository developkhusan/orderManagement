package uz.husan.ordermanagment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.husan.ordermanagment.entity.Meal;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId AND o.status = :status")
    List<Order> findAllClientIdAndStatus(@Param("clientId") Long clientId,
                                         @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId AND o.status = :status")
    Optional<Order> findByClientIdAndStatus(@Param("clientId") Long clientId,
                                            @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.deliverer.id = :deliverer AND o.status = :status")
    Optional<Order> findByDelivererIdAndStatus(@Param("deliverer") Long deliverer,
                                                @Param("status") OrderStatus status);
    Optional<Order> findById(Long integer);
}
