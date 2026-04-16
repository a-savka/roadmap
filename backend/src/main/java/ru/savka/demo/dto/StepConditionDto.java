package ru.savka.demo.dto;

public class StepConditionDto {

    private Long id;
    private String stepName;
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

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
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
