package uz.husan.ordermanagment.dto.chicken;

import lombok.Data;

@Data
public class ChickenShowDTO {
        private Long id;
        private String chickenName;
        private String chickenLocation;
        private String phoneNumber;
        private String ownerName;
        private String imageUrl;
}
