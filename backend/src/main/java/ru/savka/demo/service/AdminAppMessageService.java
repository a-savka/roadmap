package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.AppMessageDto;
import ru.savka.demo.entity.AppMessage;
import ru.savka.demo.repository.AppMessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminAppMessageService {

    private final AppMessageRepository appMessageRepository;

    public AdminAppMessageService(AppMessageRepository appMessageRepository) {
        this.appMessageRepository = appMessageRepository;
    }

    public List<AppMessageDto> getAllMessages() {
        return appMessageRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<AppMessageDto> getMessageById(Long id) {
        return appMessageRepository.findById(id).map(this::toDto);
    }

    @Transactional
    public AppMessageDto createMessage(AppMessageDto dto) {
        AppMessage message = new AppMessage();
        message.setMessageKey(dto.getMessageKey());
        message.setMessageText(dto.getMessageText());
        message.setMessageTextEn(dto.getMessageTextEn());
        message.setCategory(dto.getCategory());
        return toDto(appMessageRepository.save(message));
    }

    @Transactional
    public Optional<AppMessageDto> updateMessage(Long id, AppMessageDto dto) {
        return appMessageRepository.findById(id).map(existing -> {
            existing.setMessageKey(dto.getMessageKey());
            existing.setMessageText(dto.getMessageText());
            existing.setMessageTextEn(dto.getMessageTextEn());
            existing.setCategory(dto.getCategory());
            return toDto(appMessageRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteMessage(Long id) {
        if (appMessageRepository.existsById(id)) {
            appMessageRepository.deleteById(id);
            return true;
        }
        return false;
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
