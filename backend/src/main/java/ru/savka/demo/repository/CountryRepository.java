package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findAllByOrderByNameAsc();
}
