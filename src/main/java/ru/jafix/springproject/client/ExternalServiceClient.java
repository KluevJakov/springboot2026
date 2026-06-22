package ru.jafix.springproject.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.jafix.springproject.dto.common.StatusDto;

@FeignClient(name = "EXTERNALSERVICE")
public interface ExternalServiceClient {

    @GetMapping("/api/status")
    StatusDto getStatus();
}
