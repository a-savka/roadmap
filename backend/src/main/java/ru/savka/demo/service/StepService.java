package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.entity.Step;
import ru.savka.demo.entity.StepCondition;
import ru.savka.demo.repository.StepConditionRepository;
import ru.savka.demo.repository.StepRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepService {

    private final StepRepository stepRepository;
    private final StepConditionRepository stepConditionRepository;

    public StepService(StepRepository stepRepository, StepConditionRepository stepConditionRepository) {
        this.stepRepository = stepRepository;
        this.stepConditionRepository = stepConditionRepository;
    }

    @Transactional(readOnly = true)
    public List<StepDto> getAllSteps() {
        return stepRepository.findAllByOrderByStepOrderAsc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StepDto getStepByName(String stepName) {
        return stepRepository.findById(stepName)
                .map(this::toDto)
                .orElse(null);
    }

    private StepDto toDto(Step step) {
        StepDto dto = new StepDto();
        dto.setStepName(step.getStepName());
        dto.setStepDescription(step.getStepDescription());
        dto.setStepOrder(step.getStepOrder());
        dto.setEnabled(step.isEnabled());
        dto.setDeadlineDays(step.getDeadlineDays());
        dto.setConditions(step.getConditions().stream()
                .map(this::conditionToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private StepConditionDto conditionToDto(StepCondition condition) {
        StepConditionDto dto = new StepConditionDto();
        dto.setId(condition.getId());
        dto.setStepName(condition.getStep().getStepName());
        dto.setCountryCode(condition.getCountryCode());
        dto.setVisitPurpose(condition.getVisitPurpose());
        dto.setRelocationProgramStatus(condition.getRelocationProgramStatus());
        dto.setHqsStatus(condition.getHqsStatus());
        return dto;
    }
}
