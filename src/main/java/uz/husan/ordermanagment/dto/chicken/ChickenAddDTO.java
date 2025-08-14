package uz.husan.ordermanagment.dto.chicken;

import lombok.Data;
@Data
public class ChickenAddDTO {
    private String chickenName;
    private String chickenLocation;
    private String phoneNumber;
    private Long ownerId;
    private String imageUrl;
}