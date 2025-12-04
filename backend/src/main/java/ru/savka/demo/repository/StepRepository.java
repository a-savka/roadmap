package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.Step;

public interface StepRepository extends JpaRepository<Step, String> {
}
