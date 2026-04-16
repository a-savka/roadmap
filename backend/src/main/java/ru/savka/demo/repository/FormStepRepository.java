package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.savka.demo.entity.FormStep;
import ru.savka.demo.entity.FormStepId;

import java.util.List;

public interface FormStepRepository extends JpaRepository<FormStep, FormStepId> {
    
    @Query("SELECT fs FROM FormStep fs JOIN FETCH fs.step s WHERE fs.form.id = :formId ORDER BY s.stepOrder")
    List<FormStep> findByForm_IdWithStepsSorted(@Param("formId") Long formId);
}
