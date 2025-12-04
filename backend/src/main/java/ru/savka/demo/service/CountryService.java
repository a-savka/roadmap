package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.entity.Country;
import ru.savka.demo.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }
}
