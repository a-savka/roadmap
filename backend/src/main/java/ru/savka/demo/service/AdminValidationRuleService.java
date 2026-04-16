package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.ValidationRuleDto;
import ru.savka.demo.entity.ValidationRule;
import ru.savka.demo.repository.ValidationRuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminValidationRuleService {

    private final ValidationRuleRepository validationRuleRepository;

    public AdminValidationRuleService(ValidationRuleRepository validationRuleRepository) {
        this.validationRuleRepository = validationRuleRepository;
    }

    public List<ValidationRuleDto> getAllRules() {
        return validationRuleRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<ValidationRuleDto> getRuleById(Long id) {
        return validationRuleRepository.findById(id).map(this::toDto);
    }

    @Transactional
    public ValidationRuleDto createRule(ValidationRuleDto dto) {
        ValidationRule rule = new ValidationRule();
        rule.setCountryCode(dto.getCountryCode());
        rule.setVisitPurpose(dto.getVisitPurpose());
        rule.setRelocationProgramStatus(dto.getRelocationProgramStatus());
        rule.setHqsStatus(dto.getHqsStatus());
        rule.setMaxDays(dto.getMaxDays());
        return toDto(validationRuleRepository.save(rule));
    }

    @Transactional
    public Optional<ValidationRuleDto> updateRule(Long id, ValidationRuleDto dto) {
        return validationRuleRepository.findById(id).map(existing -> {
            existing.setCountryCode(dto.getCountryCode());
            existing.setVisitPurpose(dto.getVisitPurpose());
            existing.setRelocationProgramStatus(dto.getRelocationProgramStatus());
            existing.setHqsStatus(dto.getHqsStatus());
            existing.setMaxDays(dto.getMaxDays());
            return toDto(validationRuleRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteRule(Long id) {
        if (validationRuleRepository.existsById(id)) {
            validationRuleRepository.deleteById(id);
            return true;
        }
        return false;
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
