package ru.savka.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "step_conditions")
public class StepCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "step_name")
    @JsonBackReference
    private Step step;

    private String countryCode;

    private String visitPurpose;

    private String relocationProgramStatus;

    private String hqsStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getRelocationProgramStatus() {
        return relocationProgramStatus;
    }

    public void setRelocationProgramStatus(String relocationProgramStatus) {
        this.relocationProgramStatus = relocationProgramStatus;
    }

    public String getHqsStatus() {
        return hqsStatus;
    }

    public void setHqsStatus(String hqsStatus) {
        this.hqsStatus = hqsStatus;
    }
}
