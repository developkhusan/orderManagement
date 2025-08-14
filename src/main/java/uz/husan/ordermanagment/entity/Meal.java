package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Meal extends AbsEntity {
    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private String description;
    @ManyToOne
    @JoinColumn(name = "chicken_id")
    private Chicken chicken;
}
