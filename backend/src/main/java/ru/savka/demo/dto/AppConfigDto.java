package ru.savka.demo.dto;

import java.util.List;
import java.util.Map;

public class AppConfigDto {

    private List<CountryDto> countries;
    private List<StepDto> steps;
    private List<ValidationRuleDto> validationRules;
    private Map<String, String> messages;

    public List<CountryDto> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDto> countries) {
        this.countries = countries;
    }

    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps;
    }

    public List<ValidationRuleDto> getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(List<ValidationRuleDto> validationRules) {
        this.validationRules = validationRules;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
