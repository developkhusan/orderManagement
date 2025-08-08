package uz.husan.ordermanagment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbsEntity {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    @CreationTimestamp
    private LocalDateTime createdAt;


}
