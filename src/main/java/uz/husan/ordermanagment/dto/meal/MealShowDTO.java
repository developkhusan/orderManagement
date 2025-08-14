package uz.husan.ordermanagment.dto.meal;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MealShowDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private String chickenName;
}
