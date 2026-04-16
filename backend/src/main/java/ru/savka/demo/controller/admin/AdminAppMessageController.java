package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.AppMessageDto;
import ru.savka.demo.service.AdminAppMessageService;

import java.util.List;

@RestController
@RequestMapping("/admin/messages")
public class AdminAppMessageController {

    private final AdminAppMessageService adminAppMessageService;

    public AdminAppMessageController(AdminAppMessageService adminAppMessageService) {
        this.adminAppMessageService = adminAppMessageService;
    }

    @GetMapping
    public List<AppMessageDto> getAllMessages() {
        return adminAppMessageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppMessageDto> getMessage(@PathVariable Long id) {
        return adminAppMessageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AppMessageDto createMessage(@RequestBody AppMessageDto dto) {
        return adminAppMessageService.createMessage(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppMessageDto> updateMessage(@PathVariable Long id, @RequestBody AppMessageDto dto) {
        return adminAppMessageService.updateMessage(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (adminAppMessageService.deleteMessage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
