package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.Form;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findByUser_Username(String username);
}
