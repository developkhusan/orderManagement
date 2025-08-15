package uz.husan.ordermanagment.dto.orderItem;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import uz.husan.ordermanagment.entity.Meal;
import uz.husan.ordermanagment.entity.Order;
import uz.husan.ordermanagment.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderItemAddDTO {
   // private Long orderId;
    private Long mealId;
   // private Long chefId;
    private Integer quantity;
    //private BigDecimal unitPrice;
   // private BigDecimal totalPrice;
}
