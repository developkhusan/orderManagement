package uz.husan.ordermanagment.dto.orderItem;

import lombok.Data;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderShowDTO {
    private String clientName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime deliveryDate;
}
