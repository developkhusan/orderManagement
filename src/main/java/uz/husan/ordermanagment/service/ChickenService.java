package uz.husan.ordermanagment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import uz.husan.ordermanagment.dto.chicken.ChickenAddDTO;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.message.ResponseMessage;
@Service
public interface ChickenService {
     ResponseMessage getAllChickens( Integer page, Integer size);

     ResponseMessage createChicken( ChickenAddDTO chickenAddDTO);

     ResponseMessage updateChicken(Long id,  ChickenAddDTO chickenAddDTO);

     ResponseMessage deleteChicken(Long id);
}
