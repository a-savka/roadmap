package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.FormDto;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.entity.*;
import ru.savka.demo.exception.UserNotFoundException;
import ru.savka.demo.repository.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StepRepository stepRepository;
    private final StepConditionRepository stepConditionRepository;
    private final FormStepRepository formStepRepository;

    public FormService(FormRepository formRepository, UserRepository userRepository, 
                      CountryRepository countryRepository, StepRepository stepRepository,
                      StepConditionRepository stepConditionRepository, FormStepRepository formStepRepository) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stepRepository = stepRepository;
        this.stepConditionRepository = stepConditionRepository;
        this.formStepRepository = formStepRepository;
    }

    public Optional<Form> getUserForm(String username) {
        return formRepository.findByUser_Username(username);
    }

    @Transactional
    public Form saveNewForm(FormDto formDto) {
        User user = userRepository.findById(formDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + formDto.getUsername()));

        if (formRepository.findByUser_Username(user.getUsername()).isPresent()) {
            throw new IllegalStateException("A form for user " + user.getUsername() + " already exists. Use update endpoint.");
        }

        Country country = countryRepository.findById(formDto.getCitizenshipCountryCode())
                .orElseThrow(() -> new RuntimeException("Country not found with code: " + formDto.getCitizenshipCountryCode()));

        Form form = new Form();
        form.setUser(user);
        form.setEntryDate(formDto.getEntryDate());
        form.setCitizenshipCountry(country);
        form.setVisitPurpose(formDto.getVisitPurpose());
        form.setRelocationProgramStatus(formDto.getRelocationProgramStatus());
        form.setHqsStatus(formDto.getHqsStatus());

        Form savedForm = formRepository.save(form);

        List<Step> allSteps = stepRepository.findAllByOrderByStepOrderAsc();
        List<FormStep> formSteps = allSteps.stream()
                .filter(step -> step.isEnabled() && isStepApplicable(step, form))
                .map(step -> {
                    FormStepId formStepId = new FormStepId();
                    formStepId.setFormId(savedForm.getId());
                    formStepId.setStepName(step.getStepName());

                    FormStep formStep = new FormStep();
                    formStep.setId(formStepId);
                    formStep.setForm(savedForm);
                    formStep.setStep(step);
                    formStep.setCompleted(0);
                    return formStep;
                })
                .toList();

        formStepRepository.saveAll(formSteps);

        return savedForm;
    }

    private boolean isStepApplicable(Step step, Form form) {
        List<StepCondition> conditions = step.getConditions();
        
        if (conditions.isEmpty()) {
            return true;
        }
        
        return conditions.stream().anyMatch(condition -> matchesCondition(condition, form));
    }

    private boolean matchesCondition(StepCondition condition, Form form) {
        String countryCode = condition.getCountryCode();
        String visitPurpose = condition.getVisitPurpose();
        String relocationStatus = condition.getRelocationProgramStatus();
        String hqsStatus = condition.getHqsStatus();

        return matchesField(countryCode, form.getCitizenshipCountry().getCode()) &&
               matchesField(visitPurpose, form.getVisitPurpose()) &&
               matchesField(relocationStatus, form.getRelocationProgramStatus()) &&
               matchesField(hqsStatus, form.getHqsStatus());
    }

    private boolean matchesField(String conditionValue, String formValue) {
        return conditionValue == null || "*".equals(conditionValue) || Objects.equals(conditionValue, formValue);
    }

    @Transactional
    public Optional<Form> updateForm(Long formId, FormDto formDto) {
        return formRepository.findById(formId)
                .map(existingForm -> {
                    Country country = countryRepository.findById(formDto.getCitizenshipCountryCode())
                            .orElseThrow(() -> new RuntimeException("Country not found with code: " + formDto.getCitizenshipCountryCode()));

                    existingForm.setEntryDate(formDto.getEntryDate());
                    existingForm.setCitizenshipCountry(country);
                    existingForm.setVisitPurpose(formDto.getVisitPurpose());
                    existingForm.setRelocationProgramStatus(formDto.getRelocationProgramStatus());
                    existingForm.setHqsStatus(formDto.getHqsStatus());

                    return formRepository.save(existingForm);
                });
    }

    public Optional<Form> getFormById(Long formId) {
        return formRepository.findById(formId);
    }

    public List<StepDto> getApplicableSteps(Form form) {
        List<Step> allSteps = stepRepository.findAllByOrderByStepOrderAsc();
        return allSteps.stream()
                .filter(step -> step.isEnabled() && isStepApplicable(step, form))
                .map(this::toStepDto)
                .collect(Collectors.toList());
    }

    private StepDto toStepDto(Step step) {
        StepDto dto = new StepDto();
        dto.setStepName(step.getStepName());
        dto.setStepDescription(step.getStepDescription());
        dto.setStepOrder(step.getStepOrder());
        dto.setEnabled(step.isEnabled());
        dto.setDeadlineDays(step.getDeadlineDays());
        dto.setConditions(step.getConditions().stream()
                .map(this::toConditionDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private StepConditionDto toConditionDto(StepCondition condition) {
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
