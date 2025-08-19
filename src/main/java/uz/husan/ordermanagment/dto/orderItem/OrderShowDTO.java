package uz.husan.ordermanagment.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderShowDTO {
    private Long orderId;
    private String clientName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime deliveryDate;
}
