package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.service.AdminStepConditionService;

import java.util.List;

@RestController
@RequestMapping("/admin/step-conditions")
public class AdminStepConditionController {

    private final AdminStepConditionService adminStepConditionService;

    public AdminStepConditionController(AdminStepConditionService adminStepConditionService) {
        this.adminStepConditionService = adminStepConditionService;
    }

    @GetMapping
    public List<StepConditionDto> getAllConditions() {
        return adminStepConditionService.getAllConditions();
    }

    @PostMapping
    public ResponseEntity<StepConditionDto> createCondition(@RequestBody StepConditionDto dto) {
        return adminStepConditionService.createCondition(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepConditionDto> updateCondition(@PathVariable Long id, @RequestBody StepConditionDto dto) {
        return adminStepConditionService.updateCondition(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        if (adminStepConditionService.deleteCondition(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
