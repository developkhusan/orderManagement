package uz.husan.ordermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.husan.ordermanagment.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
