package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
