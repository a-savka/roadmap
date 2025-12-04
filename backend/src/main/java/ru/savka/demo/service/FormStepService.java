package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.FormStepDto;
import ru.savka.demo.entity.FormStep;
import ru.savka.demo.entity.FormStepId;
import ru.savka.demo.repository.FormStepRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormStepService {

    private final FormStepRepository formStepRepository;

    public FormStepService(FormStepRepository formStepRepository) {
        this.formStepRepository = formStepRepository;
    }

    public List<FormStep> getFormSteps(Long formId) {
        return formStepRepository.findByForm_Id(formId);
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
}
