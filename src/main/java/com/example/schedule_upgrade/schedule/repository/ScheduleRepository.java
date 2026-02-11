package com.example.schedule_upgrade.schedule.repository;

import com.example.schedule_upgrade.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
