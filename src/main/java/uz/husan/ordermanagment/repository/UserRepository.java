package uz.husan.ordermanagment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.entity.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmailAndConfCode(String email, String confCode);

    Page<User> findByRole(Role role, Pageable pageable);




}
