package uz.husan.ordermanagment.dto.orders;

import lombok.Data;
import uz.husan.ordermanagment.entity.enums.OrderStatus;

import java.math.BigDecimal;
@Data
public class OrdersShowDTO{
    private Long id;
    private String clientName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String orderDateTime;
}
