package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@MappedSuperclass
public abstract class AbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
