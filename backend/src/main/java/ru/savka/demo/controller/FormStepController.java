package ru.savka.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.FormStepDto;
import ru.savka.demo.entity.FormStep;
import ru.savka.demo.service.FormStepService;

import java.util.List;

@RestController
@RequestMapping("/api/form-steps")
public class FormStepController {

    private final FormStepService formStepService;

    public FormStepController(FormStepService formStepService) {
        this.formStepService = formStepService;
    }

    @GetMapping("/{formId}")
    public List<FormStep> getFormSteps(@PathVariable Long formId) {
        return formStepService.getFormSteps(formId);
    }

    @PutMapping
    public ResponseEntity<FormStep> updateStep(@RequestBody FormStepDto dto) {
        return formStepService.updateStepStatus(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
