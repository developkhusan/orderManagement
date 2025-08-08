package uz.husan.ordermanagment.dto.product;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private Integer stockQuantity;
    private Boolean isActive;
/*

    rivate String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private Boolean isActive;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;*/
    // Additional fields can be added as needed
}
