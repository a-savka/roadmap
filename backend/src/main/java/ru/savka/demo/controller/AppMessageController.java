package ru.savka.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savka.demo.dto.AppMessageDto;
import ru.savka.demo.service.AppMessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class AppMessageController {

    private final AppMessageService appMessageService;

    public AppMessageController(AppMessageService appMessageService) {
        this.appMessageService = appMessageService;
    }

    @GetMapping
    public List<AppMessageDto> getAllMessages() {
        return appMessageService.getAllMessages();
    }

    @GetMapping("/category/{category}")
    public List<AppMessageDto> getMessagesByCategory(@PathVariable String category) {
        return appMessageService.getMessagesByCategory(category);
    }

    @GetMapping("/key/{key}")
    public AppMessageDto getMessageByKey(@PathVariable String key) {
        return appMessageService.getMessageByKey(key);
    }
}
