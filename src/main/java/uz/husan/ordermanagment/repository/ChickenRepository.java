package uz.husan.ordermanagment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Chicken;

public interface ChickenRepository extends JpaRepository<Chicken, Long> {

}