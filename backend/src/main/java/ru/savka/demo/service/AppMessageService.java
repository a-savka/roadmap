package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.AppMessageDto;
import ru.savka.demo.entity.AppMessage;
import ru.savka.demo.repository.AppMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppMessageService {

    private final AppMessageRepository appMessageRepository;

    public AppMessageService(AppMessageRepository appMessageRepository) {
        this.appMessageRepository = appMessageRepository;
    }

    public List<AppMessageDto> getAllMessages() {
        return appMessageRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AppMessageDto> getMessagesByCategory(String category) {
        return appMessageRepository.findByCategory(category).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AppMessageDto getMessageByKey(String key) {
        return appMessageRepository.findByMessageKey(key)
                .map(this::toDto)
                .orElse(null);
    }

    private AppMessageDto toDto(AppMessage message) {
        AppMessageDto dto = new AppMessageDto();
        dto.setId(message.getId());
        dto.setMessageKey(message.getMessageKey());
        dto.setMessageText(message.getMessageText());
        dto.setMessageTextEn(message.getMessageTextEn());
        dto.setCategory(message.getCategory());
        return dto;
    }
}
