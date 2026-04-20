package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.FormStepDto;
import ru.savka.demo.entity.Form;
import ru.savka.demo.entity.FormStep;
import ru.savka.demo.entity.FormStepId;
import ru.savka.demo.entity.Step;
import ru.savka.demo.repository.FormRepository;
import ru.savka.demo.repository.FormStepRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class FormStepService {

    private final FormStepRepository formStepRepository;
    private final FormRepository formRepository;

    public FormStepService(FormStepRepository formStepRepository, FormRepository formRepository) {
        this.formStepRepository = formStepRepository;
        this.formRepository = formRepository;
    }

    public List<FormStepDto> getFormSteps(Long formId) {
        List<FormStep> formSteps = formStepRepository.findByForm_IdWithStepsSorted(formId);
        
        Form form = formRepository.findById(formId).orElse(null);
        LocalDate entryDate = form != null ? form.getEntryDate() : null;
        
        return formSteps.stream().map(fs -> toDto(fs, entryDate)).toList();
    }

    public Optional<FormStep> updateStepStatus(FormStepDto dto) {
        FormStepId id = new FormStepId();
        id.setFormId(dto.getFormId());
        id.setStepName(dto.getStepName());

        return formStepRepository.findById(id).map(formStep -> {
            formStep.setCompleted(dto.getCompleted());
            return formStepRepository.save(formStep);
        });
    }

    private FormStepDto toDto(FormStep formStep, LocalDate entryDate) {
        FormStepDto dto = new FormStepDto();
        dto.setFormId(formStep.getId().getFormId());
        dto.setStepName(formStep.getId().getStepName());
        dto.setCompleted(formStep.getCompleted());

        Step step = formStep.getStep();
        dto.setStepDescription(step.getStepDescription());
        dto.setStepOrder(step.getStepOrder());
        dto.setEnabled(step.isEnabled());
        dto.setDeadlineDays(step.getDeadlineDays());
        
        Integer deadlineDays = step.getDeadlineDays();
        
        if (entryDate != null && deadlineDays != null && deadlineDays > 0) {
            LocalDate deadlineDate = entryDate.plusDays(deadlineDays);
            dto.setDeadlineDate(deadlineDate);
            
            if (deadlineDate.isBefore(LocalDate.now())) {
                long overdueDays = ChronoUnit.DAYS.between(deadlineDate, LocalDate.now());
                dto.setOverdue(true);
                dto.setOverdueDays((int) overdueDays);
            }
        }
        
        return dto;
    }
}