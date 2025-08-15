package uz.husan.ordermanagment.dto.orderItem;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderIteamShowDTO {

    private Long id;
    private Long orderId;
    private String mealName;
    private String chefName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;


/*
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private String chickenName;

 */
}
