package ru.savka.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.FormDto;
import ru.savka.demo.dto.ValidationResultDto;
import ru.savka.demo.entity.Form;
import ru.savka.demo.service.FormService;
import ru.savka.demo.service.ValidationService;

import java.util.Optional;

@RestController
@RequestMapping("/forms")
public class FormController {

    private final FormService formService;
    private final ValidationService validationService;

    public FormController(FormService formService, ValidationService validationService) {
        this.formService = formService;
        this.validationService = validationService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Form> getUserForm(@PathVariable String username) {
        return formService.getUserForm(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Form saveNewForm(@RequestBody FormDto formDto) {
        return formService.saveNewForm(formDto);
    }

    @PutMapping("/{formId}")
    public ResponseEntity<Form> updateForm(@PathVariable Long formId, @RequestBody FormDto formDto) {
        return formService.updateForm(formId, formDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{formId}/validate")
    public ResponseEntity<ValidationResultDto> validateForm(@PathVariable Long formId) {
        Optional<Form> formOptional = formService.getFormById(formId);
        if (formOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Form form = formOptional.get();
        int overdueDays = validationService.calculateOverdueDays(form);
        boolean isOverdue = overdueDays > 0;

        var applicableSteps = formService.getApplicableSteps(form);
        ValidationResultDto resultDto = new ValidationResultDto(isOverdue, overdueDays, applicableSteps);
        return ResponseEntity.ok(resultDto);
    }
}
