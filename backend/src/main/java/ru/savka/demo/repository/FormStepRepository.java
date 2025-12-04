package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.FormStep;
import ru.savka.demo.entity.FormStepId;

import java.util.List;

public interface FormStepRepository extends JpaRepository<FormStep, FormStepId> {
    List<FormStep> findByForm_Id(Long formId);
}
