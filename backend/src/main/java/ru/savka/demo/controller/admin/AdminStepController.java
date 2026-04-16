package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.service.AdminStepService;

import java.util.List;

@RestController
@RequestMapping("/admin/steps")
public class AdminStepController {

    private final AdminStepService adminStepService;

    public AdminStepController(AdminStepService adminStepService) {
        this.adminStepService = adminStepService;
    }

    @GetMapping
    public List<StepDto> getAllSteps() {
        return adminStepService.getAllSteps();
    }

    @GetMapping("/{stepName}")
    public ResponseEntity<StepDto> getStep(@PathVariable String stepName) {
        return adminStepService.getStepByName(stepName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public StepDto createStep(@RequestBody StepDto dto) {
        return adminStepService.createStep(dto);
    }

    @PutMapping("/{stepName}")
    public ResponseEntity<StepDto> updateStep(@PathVariable String stepName, @RequestBody StepDto dto) {
        return adminStepService.updateStep(stepName, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{stepName}")
    public ResponseEntity<Void> deleteStep(@PathVariable String stepName) {
        if (adminStepService.deleteStep(stepName)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
