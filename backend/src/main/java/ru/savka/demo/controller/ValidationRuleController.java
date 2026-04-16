package ru.savka.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savka.demo.dto.ValidationRuleDto;
import ru.savka.demo.service.ValidationRuleService;

import java.util.List;

@RestController
@RequestMapping("/validation-rules")
public class ValidationRuleController {

    private final ValidationRuleService validationRuleService;

    public ValidationRuleController(ValidationRuleService validationRuleService) {
        this.validationRuleService = validationRuleService;
    }

    @GetMapping
    public List<ValidationRuleDto> getAllRules() {
        return validationRuleService.getAllRules();
    }
}
