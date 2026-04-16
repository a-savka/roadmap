package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.ValidationRuleDto;
import ru.savka.demo.entity.ValidationRule;
import ru.savka.demo.repository.ValidationRuleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidationRuleService {

    private final ValidationRuleRepository validationRuleRepository;

    public ValidationRuleService(ValidationRuleRepository validationRuleRepository) {
        this.validationRuleRepository = validationRuleRepository;
    }

    public List<ValidationRuleDto> getAllRules() {
        return validationRuleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ValidationRuleDto toDto(ValidationRule rule) {
        ValidationRuleDto dto = new ValidationRuleDto();
        dto.setId(rule.getId());
        dto.setCountryCode(rule.getCountryCode());
        dto.setVisitPurpose(rule.getVisitPurpose());
        dto.setRelocationProgramStatus(rule.getRelocationProgramStatus());
        dto.setHqsStatus(rule.getHqsStatus());
        dto.setMaxDays(rule.getMaxDays());
        return dto;
    }
}
