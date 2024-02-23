package ru.dimov.universityschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UniversityScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }

}
