package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.ValidationRule;

public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {
}
