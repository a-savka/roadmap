package ru.savka.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.savka.demo.entity.Form;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ValidationService {

    private final List<ValidationRule> rules = new ArrayList<>();

    public ValidationService(@Value("#{'${form.validation.rules}'.split(',')}") List<String> rulesConfig) {
        for (String ruleString : rulesConfig) {
            String[] parts = ruleString.trim().split("\\s+");
            if (parts.length == 5) {
                rules.add(new ValidationRule(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4])));
            }
        }
    }

    public int calculateOverdueDays(Form form) {
        ValidationRule bestMatch = null;
        int maxSpecificity = -1;

        for (ValidationRule rule : rules) {
            if (rule.matches(form)) {
                int specificity = rule.getSpecificity();
                if (specificity > maxSpecificity) {
                    maxSpecificity = specificity;
                    bestMatch = rule;
                }
            }
        }

        if (bestMatch != null) {
            long daysSinceEntry = ChronoUnit.DAYS.between(form.getEntryDate(), LocalDate.now());
            if (daysSinceEntry > bestMatch.days()) {
                return (int) (daysSinceEntry - bestMatch.days());
            }
        }

        return 0;
    }

    private record ValidationRule(String country, String purpose, String resettlementStatus, String hqsStatus, int days) {
        public boolean matches(Form form) {
            return (Objects.equals(country, "*") || Objects.equals(country, form.getCitizenshipCountry().getCode())) &&
                   (Objects.equals(purpose, "*") || Objects.equals(purpose, form.getVisitPurpose())) &&
                   (Objects.equals(resettlementStatus, "*") || Objects.equals(resettlementStatus, form.getRelocationProgramStatus())) &&
                   (Objects.equals(hqsStatus, "*") || Objects.equals(hqsStatus, form.getHqsStatus()));
        }

        public int getSpecificity() {
            int score = 0;
            if (!Objects.equals(country, "*")) score++;
            if (!Objects.equals(purpose, "*")) score++;
            if (!Objects.equals(resettlementStatus, "*")) score++;
            if (!Objects.equals(hqsStatus, "*")) score++;
            return score;
        }
    }
}