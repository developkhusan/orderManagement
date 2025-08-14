package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Meal;
public interface ProductRepository extends JpaRepository<Meal, Long> {
}
