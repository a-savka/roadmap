package ru.savka.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.savka.demo.entity.Country;
import ru.savka.demo.entity.Step;
import ru.savka.demo.entity.ValidationRule;
import ru.savka.demo.repository.CountryRepository;
import ru.savka.demo.repository.StepRepository;
import ru.savka.demo.repository.ValidationRuleRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final StepRepository stepRepository;
    private final ValidationRuleRepository validationRuleRepository;

    public DataLoader(CountryRepository countryRepository, StepRepository stepRepository, ValidationRuleRepository validationRuleRepository) {
        this.countryRepository = countryRepository;
        this.stepRepository = stepRepository;
        this.validationRuleRepository = validationRuleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (countryRepository.count() == 0) {
            loadCountryData();
        }
        if (stepRepository.count() == 0) {
            loadStepData();
        }
        if (validationRuleRepository.count() == 0) {
            loadValidationRulesData();
        }
    }

    private void loadCountryData() {
        Country ru = new Country();
        ru.setCode("RU");
        ru.setName("Россия");

        Country us = new Country();
        us.setCode("US");
        us.setName("США");

        Country cn = new Country();
        cn.setCode("CN");
        cn.setName("Китай");

        Country by = new Country();
        by.setCode("BY");
        by.setName("Беларусь");

        Country dz = new Country();
        dz.setCode("DZ");
        dz.setName("Алжир");

        Country ma = new Country();
        ma.setCode("MA");
        ma.setName("Марокко");

        Country kg = new Country();
        kg.setCode("KG");
        kg.setName("Киргизия");

        countryRepository.saveAll(Arrays.asList(ru, us, cn, by, dz, ma, kg));
    }

    private void loadStepData() {
        Step photo = new Step();
        photo.setStepName("photo");
        photo.setStepDescription("Сделать фотографии");
        photo.setStepOrder(1);
        photo.setEnabled(true);
        photo.setDeadlineDays(null);

        Step tax = new Step();
        tax.setStepName("tax");
        tax.setStepDescription("Подать заявление в налоговую");
        tax.setStepOrder(2);
        tax.setEnabled(true);
        tax.setDeadlineDays(null);

        stepRepository.saveAll(Arrays.asList(photo, tax));
    }

    private void loadValidationRulesData() {
        ValidationRule rule1 = new ValidationRule();
        rule1.setCountryCode("*");
        rule1.setVisitPurpose("*");
        rule1.setRelocationProgramStatus("*");
        rule1.setHqsStatus("*");
        rule1.setMaxDays(30);

        ValidationRule rule2 = new ValidationRule();
        rule2.setCountryCode("BY");
        rule2.setVisitPurpose("*");
        rule2.setRelocationProgramStatus("*");
        rule2.setHqsStatus("*");
        rule2.setMaxDays(90);

        ValidationRule rule3 = new ValidationRule();
        rule3.setCountryCode("BY");
        rule3.setVisitPurpose("work");
        rule3.setRelocationProgramStatus("no");
        rule3.setHqsStatus("yes");
        rule3.setMaxDays(180);

        ValidationRule rule4 = new ValidationRule();
        rule4.setCountryCode("BY");
        rule4.setVisitPurpose("work");
        rule4.setRelocationProgramStatus("no");
        rule4.setHqsStatus("family");
        rule4.setMaxDays(180);

        ValidationRule rule5 = new ValidationRule();
        rule5.setCountryCode("*");
        rule5.setVisitPurpose("*");
        rule5.setRelocationProgramStatus("yes");
        rule5.setHqsStatus("*");
        rule5.setMaxDays(120);

        ValidationRule rule6 = new ValidationRule();
        rule6.setCountryCode("*");
        rule6.setVisitPurpose("*");
        rule6.setRelocationProgramStatus("family");
        rule6.setHqsStatus("*");
        rule6.setMaxDays(90);

        validationRuleRepository.saveAll(Arrays.asList(rule1, rule2, rule3, rule4, rule5, rule6));
    }
}
