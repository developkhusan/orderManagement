package uz.husan.ordermanagment.dto.orderItem;

import lombok.Data;

@Data
public class OrderItemAddDTO {
    // private Long orderId;
    private Long mealId;
    // private Long chefId;
    private Integer quantity;
    //private BigDecimal unitPrice;
    // private BigDecimal totalPrice;
}