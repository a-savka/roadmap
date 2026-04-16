package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.entity.Step;
import ru.savka.demo.repository.StepRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminStepService {

    private final StepRepository stepRepository;

    public AdminStepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public List<StepDto> getAllSteps() {
        return stepRepository.findAllByOrderByStepOrderAsc().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<StepDto> getStepByName(String stepName) {
        return stepRepository.findById(stepName).map(this::toDto);
    }

    @Transactional
    public StepDto createStep(StepDto dto) {
        Step step = new Step();
        step.setStepName(dto.getStepName());
        step.setStepDescription(dto.getStepDescription());
        step.setStepOrder(dto.getStepOrder());
        step.setEnabled(dto.isEnabled());
        step.setDeadlineDays(dto.getDeadlineDays());
        Step saved = stepRepository.save(step);
        return toDto(saved);
    }

    @Transactional
    public Optional<StepDto> updateStep(String stepName, StepDto dto) {
        return stepRepository.findById(stepName).map(existing -> {
            existing.setStepDescription(dto.getStepDescription());
            existing.setStepOrder(dto.getStepOrder());
            existing.setEnabled(dto.isEnabled());
            existing.setDeadlineDays(dto.getDeadlineDays());
            return toDto(stepRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteStep(String stepName) {
        if (stepRepository.existsById(stepName)) {
            stepRepository.deleteById(stepName);
            return true;
        }
        return false;
    }

    private StepDto toDto(Step step) {
        StepDto dto = new StepDto();
        dto.setStepName(step.getStepName());
        dto.setStepDescription(step.getStepDescription());
        dto.setStepOrder(step.getStepOrder());
        dto.setEnabled(step.isEnabled());
        dto.setDeadlineDays(step.getDeadlineDays());
        dto.setConditions(List.of());
        return dto;
    }
}
