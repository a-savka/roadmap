package ru.savka.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.savka.demo.entity.Country;
import ru.savka.demo.entity.Step;
import ru.savka.demo.repository.CountryRepository;
import ru.savka.demo.repository.StepRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final StepRepository stepRepository;

    public DataLoader(CountryRepository countryRepository, StepRepository stepRepository) {
        this.countryRepository = countryRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (countryRepository.count() == 0) {
            loadCountryData();
        }
        if (stepRepository.count() == 0) {
            loadStepData();
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

        Step tax = new Step();
        tax.setStepName("tax");
        tax.setStepDescription("Подать заявление в налоговую");

        stepRepository.saveAll(Arrays.asList(photo, tax));
    }
}
