package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.dto.FormDto;
import ru.savka.demo.entity.*;
import ru.savka.demo.exception.UserNotFoundException;
import ru.savka.demo.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StepRepository stepRepository;
    private final FormStepRepository formStepRepository;

    public FormService(FormRepository formRepository, UserRepository userRepository, CountryRepository countryRepository, StepRepository stepRepository, FormStepRepository formStepRepository) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stepRepository = stepRepository;
        this.formStepRepository = formStepRepository;
    }

    public Optional<Form> getUserForm(String username) {
        return formRepository.findByUser_Username(username);
    }

    @Transactional
    public Form saveNewForm(FormDto formDto) {
        User user = userRepository.findById(formDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + formDto.getUsername()));

        // Prevent creating a new form if one already exists for the user
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

        // Create initial steps for the new form
        List<Step> allSteps = stepRepository.findAll();
        List<FormStep> formSteps = allSteps.stream().map(step -> {
            FormStepId formStepId = new FormStepId();
            formStepId.setFormId(savedForm.getId());
            formStepId.setStepName(step.getStepName());

            FormStep formStep = new FormStep();
            formStep.setId(formStepId);
            formStep.setForm(savedForm);
            formStep.setStep(step);
            formStep.setCompleted(0);
            return formStep;
        }).collect(Collectors.toList());

        formStepRepository.saveAll(formSteps);

        return savedForm;
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
}
