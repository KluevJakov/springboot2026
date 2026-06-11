package ru.jafix.springproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.jafix.springproject.service.TaskService;

@EnableScheduling
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ScheduledConfig {

    private final TaskService taskService;


    //@Scheduled(cron = "*/30 * * * * *", zone = "Europe/Saratov")
    public void increaseScoreEveryNight() {
        log.info("Запуск задачи каждые 30 секунд");
        taskService.increaseScore(5);
    }
}
