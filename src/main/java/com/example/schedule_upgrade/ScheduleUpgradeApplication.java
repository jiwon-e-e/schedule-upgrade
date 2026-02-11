package com.example.schedule_upgrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleUpgradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleUpgradeApplication.class, args);
    }

}
