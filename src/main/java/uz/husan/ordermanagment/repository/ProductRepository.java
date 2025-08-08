package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
