package uz.husan.ordermanagment.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketShowDTO {
    private Long orderId;
    private BigDecimal totalAmount;
    private List<OrderIteamShowDTO> items;
}
