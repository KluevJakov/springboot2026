package ru.jafix.springproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.springproject.dto.common.StatusDto;
import ru.jafix.springproject.kafka.KafkaProducer;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @PostMapping
    public void getStatus(@RequestBody StatusDto statusDto) {
        String statusStr = objectMapper.writeValueAsString(statusDto);
        kafkaProducer.publishToKafka(UUID.randomUUID(), statusStr);
    }

}
