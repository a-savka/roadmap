package ru.savka.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savka.demo.dto.AppConfigDto;
import ru.savka.demo.service.ConfigService;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public AppConfigDto getFullConfig() {
        return configService.getFullConfig();
    }
}
