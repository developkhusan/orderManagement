package uz.husan.ordermanagment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
    @Override
    Page<Meal> findAll(Pageable pageable);
    Page<Meal> findByChickenId(Long chickenId, Pageable pageable);
}
