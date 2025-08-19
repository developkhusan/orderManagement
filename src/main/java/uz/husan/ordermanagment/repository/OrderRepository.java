package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Optional<Order> findByClientId(Long clientId);
    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId AND o.status = :status")
    Optional<Order> findByClientIdAndStatus(@Param("clientId") Long clientId,
                                            @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId AND o.status = :status")
    List<Order> findAllClientIdAndStatus(@Param("clientId") Long clientId,
                                        @Param("status") OrderStatus status);

}
