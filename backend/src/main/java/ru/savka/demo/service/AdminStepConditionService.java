package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.entity.StepCondition;
import ru.savka.demo.repository.StepConditionRepository;
import ru.savka.demo.repository.StepRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminStepConditionService {

    private final StepConditionRepository stepConditionRepository;
    private final StepRepository stepRepository;

    public AdminStepConditionService(StepConditionRepository stepConditionRepository, StepRepository stepRepository) {
        this.stepConditionRepository = stepConditionRepository;
        this.stepRepository = stepRepository;
    }

    public List<StepConditionDto> getAllConditions() {
        return stepConditionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<StepConditionDto> getConditionsForStep(String stepName) {
        return stepConditionRepository.findByStep_StepName(stepName).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public Optional<StepConditionDto> createCondition(StepConditionDto dto) {
        return stepRepository.findById(dto.getStepName()).map(step -> {
            StepCondition condition = new StepCondition();
            condition.setStep(step);
            condition.setCountryCode(dto.getCountryCode());
            condition.setVisitPurpose(dto.getVisitPurpose());
            condition.setRelocationProgramStatus(dto.getRelocationProgramStatus());
            condition.setHqsStatus(dto.getHqsStatus());
            return toDto(stepConditionRepository.save(condition));
        });
    }

    @Transactional
    public Optional<StepConditionDto> updateCondition(Long id, StepConditionDto dto) {
        return stepConditionRepository.findById(id).map(existing -> {
            stepRepository.findById(dto.getStepName()).ifPresent(existing::setStep);
            existing.setCountryCode(dto.getCountryCode());
            existing.setVisitPurpose(dto.getVisitPurpose());
            existing.setRelocationProgramStatus(dto.getRelocationProgramStatus());
            existing.setHqsStatus(dto.getHqsStatus());
            return toDto(stepConditionRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteCondition(Long id) {
        if (stepConditionRepository.existsById(id)) {
            stepConditionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private StepConditionDto toDto(StepCondition condition) {
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
