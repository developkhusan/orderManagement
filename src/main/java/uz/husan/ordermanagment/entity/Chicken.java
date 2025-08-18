package uz.husan.ordermanagment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
public class Chicken extends AbsEntity{
    private String chickenName;
    private String chickenLocation;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;
    private BigDecimal balance;
    private String imageUrl;
}
