package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.AppConfigDto;
import ru.savka.demo.dto.CountryDto;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.dto.ValidationRuleDto;
import ru.savka.demo.entity.Country;
import ru.savka.demo.entity.AppMessage;
import ru.savka.demo.repository.AppMessageRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConfigService {

    private final CountryService countryService;
    private final StepService stepService;
    private final ValidationRuleService validationRuleService;
    private final AppMessageRepository appMessageRepository;

    public ConfigService(CountryService countryService, StepService stepService,
                         ValidationRuleService validationRuleService, AppMessageRepository appMessageRepository) {
        this.countryService = countryService;
        this.stepService = stepService;
        this.validationRuleService = validationRuleService;
        this.appMessageRepository = appMessageRepository;
    }

    public AppConfigDto getFullConfig() {
        AppConfigDto config = new AppConfigDto();

        List<Country> countries = countryService.getAllCountries();
        List<CountryDto> countryDtos = countries.stream()
                .map(this::toCountryDto)
                .toList();
        config.setCountries(countryDtos);

        List<StepDto> steps = stepService.getAllSteps();
        config.setSteps(steps);

        List<ValidationRuleDto> validationRules = validationRuleService.getAllRules();
        config.setValidationRules(validationRules);

        Map<String, String> messages = appMessageRepository.findAll().stream()
                .collect(Collectors.toMap(AppMessage::getMessageKey, AppMessage::getMessageText));
        config.setMessages(messages);

        return config;
    }

    private CountryDto toCountryDto(Country country) {
        CountryDto dto = new CountryDto();
        dto.setCode(country.getCode());
        dto.setName(country.getName());
        return dto;
    }
}
