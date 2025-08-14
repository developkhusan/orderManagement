package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.meal.MealAddDTO;
import uz.husan.ordermanagment.dto.meal.MealShowDTO;
import uz.husan.ordermanagment.entity.Chicken;
import uz.husan.ordermanagment.entity.Meal;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.ChickenRepository;
import uz.husan.ordermanagment.repository.MealRepository;
import uz.husan.ordermanagment.service.MealService;

import java.util.Optional;

@RequiredArgsConstructor
@Service("MealService")
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final ChickenRepository chickenRepository;
    public MealShowDTO getMeal(Meal meal) {
        MealShowDTO mealShowDTO = new MealShowDTO();
        mealShowDTO.setId(meal.getId());
        mealShowDTO.setName(meal.getName());
        mealShowDTO.setDescription(meal.getDescription());
        mealShowDTO.setPrice(meal.getPrice());
        mealShowDTO.setCategory(meal.getCategory());
        mealShowDTO.setImageUrl(meal.getImageUrl());
        mealShowDTO.setChickenName(meal.getChicken().getChickenName());
        return mealShowDTO;
    }
    @Override
    public ResponseMessage getAllMeals(Integer page, Integer size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<MealShowDTO> all = mealRepository.findAll(pageRequest).map(this::getMeal );
        if (all.isEmpty()){
            return ResponseMessage.builder().success(false).message("Cards no such exists").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("meals fetched successfully")
                .success(true)
                .data(all)
                .build();
    }

    @Override
    public ResponseMessage createMeal(MealAddDTO mealAddDTO) {
        Optional<Chicken> chicken = chickenRepository.findById(mealAddDTO.getChickenId());
        if( chicken.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Chicken not found")
                    .data(null)
                    .build();
        }
        Meal meal = new Meal();
        meal.setName(mealAddDTO.getName());
        meal.setDescription(mealAddDTO.getDescription());
        meal.setPrice(mealAddDTO.getPrice());
        meal.setCategory(mealAddDTO.getCategory());
        meal.setImageUrl(mealAddDTO.getImageUrl());
        meal.setChicken(chicken.get());
        Meal savedMeal = mealRepository.save(meal);
        return ResponseMessage.builder()
                .success(true)
                .message("Meal created successfully")
                .data(getMeal(savedMeal))
                .build();
    }

    @Override
    public ResponseMessage updateMeal(Long id, MealAddDTO productDTO) {
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        if (optionalMeal.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Meal not found")
                    .data(null)
                    .build();
        }
        Meal meal = optionalMeal.get();
        meal.setName(productDTO.getName());
        meal.setDescription(productDTO.getDescription());
        meal.setPrice(productDTO.getPrice());
        meal.setCategory(productDTO.getCategory());
        meal.setImageUrl(productDTO.getImageUrl());
        Meal updatedMeal = mealRepository.save(meal);
        return ResponseMessage.builder()
                .success(true)
                .message("Meal updated successfully")
                .data(getMeal(updatedMeal))
                .build();
    }

    @Override
    public ResponseMessage deleteMeal(Long id) {
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        if (optionalMeal.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Meal not found")
                    .data(null)
                    .build();
        }
        mealRepository.delete(optionalMeal.get());
        return ResponseMessage.builder()
                .success(true)
                .message("Meal deleted successfully")
                .data(null)
                .build();
    }
}
