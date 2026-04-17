package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.service.AdminStepConditionService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/steps/{stepName}/conditions")
public class AdminStepConditionController {

    private final AdminStepConditionService adminStepConditionService;

    public AdminStepConditionController(AdminStepConditionService adminStepConditionService) {
        this.adminStepConditionService = adminStepConditionService;
    }

    @GetMapping
    public List<StepConditionDto> getConditionsForStep(@PathVariable String stepName) {
        return adminStepConditionService.getConditionsForStep(stepName);
    }

    @PostMapping
    public ResponseEntity<StepConditionDto> createCondition(@PathVariable String stepName, @RequestBody StepConditionDto dto) {
        dto.setStepName(stepName);
        return adminStepConditionService.createCondition(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepConditionDto> updateCondition(@PathVariable String stepName, @PathVariable Long id, @RequestBody StepConditionDto dto) {
        return adminStepConditionService.updateCondition(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable String stepName, @PathVariable Long id) {
        if (adminStepConditionService.deleteCondition(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}