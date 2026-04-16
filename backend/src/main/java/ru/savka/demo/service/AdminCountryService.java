package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savka.demo.entity.Country;
import ru.savka.demo.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminCountryService {

    private final CountryRepository countryRepository;

    public AdminCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findById(code);
    }

    @Transactional
    public Country createCountry(String code, String name) {
        Country country = new Country();
        country.setCode(code);
        country.setName(name);
        return countryRepository.save(country);
    }

    @Transactional
    public Optional<Country> updateCountry(String code, String name) {
        return countryRepository.findById(code).map(existing -> {
            existing.setName(name);
            return countryRepository.save(existing);
        });
    }

    @Transactional
    public boolean deleteCountry(String code) {
        if (countryRepository.existsById(code)) {
            countryRepository.deleteById(code);
            return true;
        }
        return false;
    }
}
