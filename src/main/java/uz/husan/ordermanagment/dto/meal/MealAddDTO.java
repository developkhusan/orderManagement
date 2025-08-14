package uz.husan.ordermanagment.dto.meal;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import uz.husan.ordermanagment.entity.Chicken;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MealAddDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private Long chickenId;
}


