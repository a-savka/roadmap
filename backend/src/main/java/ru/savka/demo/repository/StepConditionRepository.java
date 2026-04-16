package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.savka.demo.entity.StepCondition;

import java.util.List;

@Repository
public interface StepConditionRepository extends JpaRepository<StepCondition, Long> {
    List<StepCondition> findByStep_StepName(String stepName);
}
