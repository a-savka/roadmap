package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.FormDto;
import ru.savka.demo.dto.StepConditionDto;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.entity.Country;
import ru.savka.demo.entity.Form;
import ru.savka.demo.entity.Step;
import ru.savka.demo.entity.StepCondition;
import ru.savka.demo.entity.User;
import ru.savka.demo.exception.UserNotFoundException;
import ru.savka.demo.repository.CountryRepository;
import ru.savka.demo.repository.FormRepository;
import ru.savka.demo.repository.StepRepository;
import ru.savka.demo.repository.UserRepository;
import ru.savka.demo.rule.FieldCondition;
import ru.savka.demo.rule.RuleValidationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StepRepository stepRepository;
    private final RuleValidationService ruleValidationService;

    public FormService(FormRepository formRepository, UserRepository userRepository, 
                      CountryRepository countryRepository, StepRepository stepRepository,
                      RuleValidationService ruleValidationService) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stepRepository = stepRepository;
        this.ruleValidationService = ruleValidationService;
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

        return formRepository.save(form);
    }

    private boolean isStepApplicable(Step step, Form form) {
        List<StepCondition> conditions = step.getConditions();
        if (conditions.isEmpty()) {
            return true;
        }

        Map<String, String> context = Map.of(
            "countryCode", form.getCitizenshipCountry().getCode(),
            "visitPurpose", form.getVisitPurpose(),
            "relocationProgramStatus", form.getRelocationProgramStatus(),
            "hqsStatus", form.getHqsStatus()
        );

        return conditions.stream().anyMatch(sc -> {
            List<FieldCondition> fieldConditions = List.of(
                new FieldCondition("countryCode", sc.getCountryCode()),
                new FieldCondition("visitPurpose", sc.getVisitPurpose()),
                new FieldCondition("relocationProgramStatus", sc.getRelocationProgramStatus()),
                new FieldCondition("hqsStatus", sc.getHqsStatus())
            );
            return ruleValidationService.evaluate(fieldConditions, context);
        });
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
