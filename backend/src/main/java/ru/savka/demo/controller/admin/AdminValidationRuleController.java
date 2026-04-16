package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.ValidationRuleDto;
import ru.savka.demo.service.AdminValidationRuleService;

import java.util.List;

@RestController
@RequestMapping("/admin/validation-rules")
public class AdminValidationRuleController {

    private final AdminValidationRuleService adminValidationRuleService;

    public AdminValidationRuleController(AdminValidationRuleService adminValidationRuleService) {
        this.adminValidationRuleService = adminValidationRuleService;
    }

    @GetMapping
    public List<ValidationRuleDto> getAllRules() {
        return adminValidationRuleService.getAllRules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValidationRuleDto> getRule(@PathVariable Long id) {
        return adminValidationRuleService.getRuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ValidationRuleDto createRule(@RequestBody ValidationRuleDto dto) {
        return adminValidationRuleService.createRule(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValidationRuleDto> updateRule(@PathVariable Long id, @RequestBody ValidationRuleDto dto) {
        return adminValidationRuleService.updateRule(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        if (adminValidationRuleService.deleteRule(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
