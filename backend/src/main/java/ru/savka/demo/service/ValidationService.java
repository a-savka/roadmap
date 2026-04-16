package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.entity.Form;
import ru.savka.demo.entity.ValidationRule;
import ru.savka.demo.repository.ValidationRuleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class ValidationService {

    private final ValidationRuleRepository validationRuleRepository;

    public ValidationService(ValidationRuleRepository validationRuleRepository) {
        this.validationRuleRepository = validationRuleRepository;
    }

    public int calculateOverdueDays(Form form) {
        List<ValidationRule> rules = validationRuleRepository.findAll();

        ValidationRule bestMatch = null;
        int maxSpecificity = -1;

        for (ValidationRule rule : rules) {
            if (matchesRule(rule, form)) {
                int specificity = getSpecificity(rule);
                if (specificity > maxSpecificity) {
                    maxSpecificity = specificity;
                    bestMatch = rule;
                }
            }
        }

        if (bestMatch != null) {
            long daysSinceEntry = ChronoUnit.DAYS.between(form.getEntryDate(), LocalDate.now());
            if (daysSinceEntry > bestMatch.getMaxDays()) {
                return (int) (daysSinceEntry - bestMatch.getMaxDays());
            }
        }

        return 0;
    }

    private boolean matchesRule(ValidationRule rule, Form form) {
        String country = rule.getCountryCode();
        String purpose = rule.getVisitPurpose();
        String resettlementStatus = rule.getRelocationProgramStatus();
        String hqsStatus = rule.getHqsStatus();

        return (Objects.equals(country, "*") || country == null || Objects.equals(country, form.getCitizenshipCountry().getCode())) &&
               (Objects.equals(purpose, "*") || purpose == null || Objects.equals(purpose, form.getVisitPurpose())) &&
               (Objects.equals(resettlementStatus, "*") || resettlementStatus == null || Objects.equals(resettlementStatus, form.getRelocationProgramStatus())) &&
               (Objects.equals(hqsStatus, "*") || hqsStatus == null || Objects.equals(hqsStatus, form.getHqsStatus()));
    }

    private int getSpecificity(ValidationRule rule) {
        int score = 0;
        if (!Objects.equals(rule.getCountryCode(), "*") && rule.getCountryCode() != null) score++;
        if (!Objects.equals(rule.getVisitPurpose(), "*") && rule.getVisitPurpose() != null) score++;
        if (!Objects.equals(rule.getRelocationProgramStatus(), "*") && rule.getRelocationProgramStatus() != null) score++;
        if (!Objects.equals(rule.getHqsStatus(), "*") && rule.getHqsStatus() != null) score++;
        return score;
    }
}
